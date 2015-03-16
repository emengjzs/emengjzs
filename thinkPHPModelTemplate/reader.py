__author__ = 'emengjzs'
# -*- coding:utf-8-*-
import writer
class Reader:
    file = None
    buffer = None

    def __init__(self, name):
        self.file = open(name, 'r')

    def close(self):
        self.file.close()

    def hasNextLine(self):
        if buffer is None:
            self.readLine()
        return self.buffer != ''

    def readLine(self):
        self.buffer = self.file.readline()
        return self.buffer.strip('\n')


class DBReader:
    reader = None
    is_end = False

    def __init__(self):
        self.reader = Reader("dbmodel.txt")

    def hasNext(self):
        return self.reader.hasNextLine()

    def get_model(self):
        if self.reader.hasNextLine():

            comment = []
            while self.reader.hasNextLine():
                string = self.reader.readLine()
                if string.startswith('//'):
                    comment.append(string[2:])
                else:
                    name = string
                    break
                
            model = writer.ModelWriter(name)
            model.comment = comment
            index = 0
            model.field[writer.Voc._type] = {}
            while self.reader.hasNextLine():
                string = self.reader.readLine()

                if string == '':
                    return model

                args = string.split(" ")
                print args
                if args[0].startswith("-pk"):
                    model.field[writer.Voc._pk] = args[1]
                else:
                    model.field[index] = args[0]
                    index += 1
                    model.field[writer.Voc._type][args[0]] = args[1]

    def close(self):
        self.reader.close()

def run():
    r = DBReader()
    while r.hasNext():
        model = r.get_model()
        model.write_sql()
    r.close()

run()
