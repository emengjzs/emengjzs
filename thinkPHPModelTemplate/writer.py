__author__ = 'emengjzs'
# -*- coding:utf-8-*-
import codecs
import datetime

class Writer:

    path = ''
    file = None
    first = True

    def __init__(self, name, reserve=False):
        
        self.path = name
        if reserve:
            mode = 'a'
        else:
            mode = 'w'
        self.file = codecs.open(name, mode, "utf-8")

    def write(self, str):
        self.file.write(unicode(str, "utf-8"))

    def endLine(self):
        self.write("\n")

    def writeLine(self, str):
        if self.first:
            self.write(str)
            self.first = False
        else:
            self.write("\n" + str)

    def close(self):
        if self.file:
            self.file.close()

class Voc:
    _array = "array"
    _protected = "protected"
    _class = "class"
    _pk = "_pk"
    _php_beg = "<?php"
    _extends = "extends"
    _type = "_type"
    _tab = " " * 4
    _fields = "$fields"
    _use = 'use Think\Model;'
    _ns = 'namespace Home\Model;'

class ModelWriter:
    tab_stack = 0
    writer = None
    modelname = ''
    postfix = ".class.php"
    pk = ''
    field = {}
    buffer = None
    commment = []
    info = ["@Author: jzs","@Corparation: Micourse"]

    def __init__(self, name):
        self.modelname = name
        self.tab_stack = 0
        self.writer = None
        self.postfix = ".class.php"
        self.pk = ''
        self.field = {}
        self.buffer = None
        self.commment = []

    def write_class(self):
        self.writer = Writer("model/" + self.getFileName())
        self.write_class_beg()
        self.write_field()
        self.write_class_end()
        self.writer.close()

    def write_sql(self):
        self.writer = Writer("sql/" + "result.sql", True)
        self.writeLine("\n")
        self.writeLine("drop table if exists " + self.modelname + ";")
        self.writeLine("create table " + self.modelname + " (")

        self.add_indent()
        if self.field[Voc._pk]:
            id_name = self.field[Voc._pk]
        for k, v in self.field.iteritems():
            if k == Voc._pk or k == Voc._type:
                continue
            if id_name == v:
                self.writeLine(make_sen([v, self.field[Voc._type][v], "not null,"]))
            else:
                self.writeLine(make_sen([v, self.field[Voc._type][v] + ","]))
        self.writeLine(make_sen(["primary", "key(", id_name, ")"]))


        self.remove_indent()
        self.writeLine(");")
        self.writer.close()


    def write_class_beg(self):
        self.writeLine(Voc._php_beg)
        self.write_comment()
        self.writeLine(Voc._ns)
        self.writeLine(Voc._use)
        text = [
                make_sen(["class", self.getClassName(), Voc._extends,  "Model", "{"])
                ]
        self.writeLines(text)
        self.add_indent()

    def write_comment(self):
        self.writeLine('/*')
        self.writeLines([ ' * ' + c for c in self.comment])
        self.writeLines([ ' * ' + s for s in self.info])
        self.writeLine(' * ' + "@Date: " + get_now_str())
        self.writeLine(' */');
    
    def write_class_end(self):
        self.remove_indent()
        self.writeLine("}")

    def writeLines(self, text):
        for t in text:
            self.writeLine(t)

    def getFileName(self):
        return self.getClassName() + self.postfix

    def getClassName(self):
        str = self.modelname.split('_')
        return ''.join([n.capitalize() for n in str]) + "Model"

    def add_indent(self):
        self.tab_stack += 1

    def remove_indent(self):
        self.tab_stack -= 1

    def writeLine(self, str):
        self.writer.writeLine(Voc._tab * self.tab_stack + str)

    def write(self, str):
        if not self.buffer:
            self.buffer = str
        else:
            self.buffer += str

    def endLine(self):
        if self.buffer:
            self.writeLine(self.buffer)
            self.buffer = None

    def write_array(self, array):
        max_flen = get_max_field_len(array)
        self.write(" array(")
        self.endLine()
        self.add_indent()
        sorted_array = sorted(array.iteritems(), key=lambda x:x[0])
        for s in sorted_array:
            k = s[0]
            v = s[1]
            if type(k) == int:
                t = str(k)
                self.write(make_sen([w_left(t, max_flen), '=>', '']))
            else:
                self.write(make_sen([w_left(wrap(k), max_flen), '=>', '']))
            if type(v) == str:
                self.write(wrap(v))
            elif type(v) == dict:
                self.write_array(v)
            self.write(",")
            self.endLine()

        self.remove_indent()
        self.write(")")

    def write_field(self):
        self.write(make_sen([Voc._protected, Voc._fields, '=']))
        self.write_array(self.field)
        self.write(";")
        self.endLine()



def get_now_str():
    return datetime.datetime.now().strftime("%Y/%m/%d")

def get_max_field_len(array):
    if array:
        return max([len(str(s)) for s in array.keys()]) + 2
    else:
        return len(array)

def w_left(s, l):
    return s + " " * (l - len(s))

def wrap(str, sign="'"):
    return sign + str + sign

def make_sen(words):
    return " ".join(words)
