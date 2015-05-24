# -*- coding:utf-8-*-
import sqlite3

import timehelper
import Console


ROOT = "Documents"

path = open("QQpath.jzs", "r").read() + ROOT
QQ = open("QQnumber.jzs", "r").read()


class DBConnector:
    qq = ""
    db = None
    import re

    p = re.compile(r'(delete|update|drop|create)', re.IGNORECASE)

    def __init__(self, qq_str):
        if str(qq_str).isdigit():
            self.qq = qq_str
        else:
            print "Invalid QQ Number!"

    def connect(self):
        db = sqlite3.connect("\\".join([path, "contents", self.qq, "QQ.db"]))
        self.db = db

    def disconnect(self):
        self.db.close()

    def exec_sql(self, sql):
        if self.p.search(sql):
            print "Forbidden SQL."
            return
        else:
            cu = self.db.cursor()
            cu.execute(sql)
            result = cu.fetchall()
            cu.close()
            return result


class FriendList:
    """
    [(qq, ctime), ...]
    """
    FIELD = ['qq', 'time']
    flist = []
    db = None
    SQL = """SELECT """ + ", ".join(FIELD) + """ FROM lincqqlist ORDER BY time DESC;"""

    def __init__(self, db):
        if isinstance(db, DBConnector):
            self.flist = db.exec_sql(self.SQL)
            self.db = db

    def __check__(self, no):
        if no < 0 or len(self.flist) <= no:
            Console.print_temp("Invalid No.")
            import time
            time.sleep(1)
            return False
        return True

    def get_recent_friend_list(self):
        return self.flist

    def print_list(self):
        flist = [(f[0], timehelper.get_format_ftime(f[1])) for f in self.flist]
        Console.show_in_froms([u"QQ", u"最近联系时间"], flist, True)

    def show_friend_dialog(self, no):
        if self.__check__(no):
            fm = FriendMessages(self.flist[no][0], self.db)
            fm.show_message()

    def show_friend_date(self, no):
        if self.__check__(no):
            fm = FriendMessages(self.flist[no][0], self.db)
            Console.show_in_froms(["Date"], fm.get_date_list(), True)

    def show_friends_dialog(self):
        for no in range(len(self.flist)):
            self.show_friend_dialog(no)


class GroupList:
    TABLE = "tb_troop"
    flist = []
    SQL = """SELECT groupcode, GroupName FROM """ + TABLE + """  ORDER BY groupid;"""

    def __init__(self, db):
        if isinstance(db, DBConnector):
            self.flist = db.exec_sql(self.SQL)

    def get_recent_friend_list(self):
        return self.flist

    def print_list(self):
        Console.show_in_froms(["GroupID", "GroupName"], self.flist, True)


class QQMessage:
    MINE = 0
    SELF = 1
    FIELD = ["content", "time", "flag"]
    content = ""
    time = ""
    flag = 0

    def __init__(self, content, c_time, c_flag):
        self.content = content
        self.time = c_time
        self.flag = c_flag


class FriendMessages:
    qq = ""
    name = ""
    Messages = []
    SIGN = (u"○  ", u"●  ")

    def __init__(self, qq, db):
        if str(qq).isdigit() and isinstance(db, DBConnector):
            self.qq = qq
            result = db.exec_sql("""SELECT """ + ", ".join(QQMessage.FIELD) + """ FROM tb_message WHERE uin=""" + qq
                                 + """ ORDER BY time ASC;""")
        else:
            print "Forbidden Action."
        self.Messages = [QQMessage(r[0], r[1], r[2]) for r in result]

    def get_date_list(self, flag=timehelper.Day):
        date_list = []
        flag += 1
        if self.Messages:
            sd = timehelper.get_time(self.Messages[0].time)
            date_list.append(sd)
            for m in self.Messages:
                date = timehelper.get_time(m.time)
                for i in range(0, flag):
                    if date[i] != sd[i]:
                        date_list.append(date)
                        sd = date
                        break
        return [timehelper.get_format_ftime_l(d, timehelper.Day) for d in date_list]

    def show_message(self):
        Console.show_in_box("QQ: " + self.qq, True)
        if self.Messages:
            start_date = timehelper.get_time(self.Messages[0].time)
            Console.show_in_box(timehelper.get_format_ftime(self.Messages[0].time))
            for i in range(0, len(self.Messages)):
                m = self.Messages[i]
                date = timehelper.get_time(m.time)
                if date[timehelper.Day] != start_date[timehelper.Day]:
                    start_date = date
                    Console.show_in_box(timehelper.get_format_ftime(m.time))
                try:
                    print timehelper.get_format_stime(m.time) + " " + \
                        self.SIGN[m.flag] + \
                        m.content
                except UnicodeEncodeError:
                    # print e
                    continue

        else:
            Console.print_temp("No any details.")


class Processor:
    dbc = None
    fl = None

    def __init__(self, dbc):
        if isinstance(dbc, DBConnector):
            self.dbc = dbc

    def process(self, order):
        texts = order.split(" ")
        length = len(texts)
        if texts[0] == "show":
            if texts[1] == "friends":
                self.fl = FriendList(self.dbc)
                self.fl.print_list()
            if self.fl:
                if texts[1] == "friend":
                    if texts[2].isdigit():
                        if texts[3] == "dialog":
                            self.fl.show_friend_dialog(int(texts[2]))
                        if texts[3] == "date":
                            self.fl.show_friend_date(int(texts[2]))

                    if texts[2] == "all":
                        self.fl.show_friends_dialog()

            if texts[1] == "groups":
                gl = GroupList(self.dbc)
                gl.print_list()


def start():
    Console.show_in_box("QQ Documents Reader\n"
                        "Create By emengjzs\n"
                        "Use For iPhone QQ 2.2 Only\n"
                        "DO NOT USE FOR COMERCIAL PROPOSE", True)
    print "QQ: " + QQ

    dbc = DBConnector(QQ)
    dbc.connect()
    pc = Processor(dbc)

    while True:
        order = raw_input("::")
        if order == "exit":
            dbc.disconnect()
            exit()
        else:
            pc.process(order)


start()
