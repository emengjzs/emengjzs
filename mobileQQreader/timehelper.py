__author__ = 'jzs'
# -*- coding:utf-8-*-
import time
Year = 0
Month = 1
Day = 2
hour = 3
minute = 4
second = 5

OFF_SET = [2, 5, 11, 14, 16, 16]
FORMAT = "%Y-%m-%d %A %H:%M"


def get_format_ftime(seconds):
    return time.strftime("%Y-%m-%d %A %H:%M", time.localtime(seconds))


def get_format_ftime_l(date, flag=second):
    return time.strftime(FORMAT[:OFF_SET[flag]], date)


def get_format_stime(seconds):
    return time.strftime("%H:%M", time.localtime(seconds))


def get_time(seconds):
    return time.localtime(seconds)