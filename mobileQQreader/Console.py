__author__ = 'jzs'
# -*- coding:utf-8-*-
import os
import sys


BOARDER = 8


def show_in_box(s, lr_board=False, sign='-'):
    str_list = s.split('\n')
    length = max([t_len(s) for s in str_list]) + 30
    split = sign * length
    print split
    if lr_board:
        for s in str_list:
            print "|" + s.center(length - 2) + "|"
    else:
        print s.center(length)
    print split


def t_len(string):
    if isinstance(string, int):
        return len(str(string))
    length = 0
    for s in string:
        if is_chinese(s):
            length += 2
        else:
            length += 1
    return length


def s_len(string):
    if isinstance(string, int):
        return len(str(string))
    return len(string)


def count_chi(string):
    if isinstance(string, int) or isinstance(string, float):
        return 0
    length = 0
    for s in string:
        if is_chinese(s):
            length += 1
    return length


def t_str(i):
    if isinstance(i, int) or isinstance(i, float):
        return str(i)
    return i


def show_in_froms(titles, rows, num=False, hsign='-', vsign = '|'):
    if not (isinstance(rows[0], tuple) or isinstance(rows[0], list)):
        rows = [[r] for r in rows]
    col_n = len(titles)
    max_width = [max([t_len((r[j])) for r in rows]) for j in range(col_n)]
    for i in range(col_n):
        max_width[i] = max(max_width[i], t_len(titles[i]))
    length = sum(max_width) + (BOARDER + 1) * col_n + 2

    if num:
        num_length = len(rows) % 10 + 1
        length += num_length + 1

    print hsign * length
    tstr = vsign

    if num:
        tstr += "No".center(num_length) + vsign

    tstr += vsign.join([titles[t].center(-count_chi(titles[t]) + max_width[t] + BOARDER) for t in range(col_n)])
    print tstr, vsign
    print hsign * length
    for i in range(len(rows)):
        r = rows[i]
        tstr = vsign
        if num:
            tstr += str(i).center(num_length) + vsign
        tstr += vsign.join([("  " + t_str(r[t])).ljust(-count_chi(r[t]) + max_width[t] + BOARDER)for t in range(col_n)])
        print tstr, vsign
    print hsign * length


def print_temp(s):
    os.write(1, s)
    sys.stdout.write("\r")
    sys.stdout.flush()


def is_chinese(uchar):
    """判断一个unicode是否是汉字"""
    if u'\u4e00' <= uchar <= u'\u9fa5':
        return True
    else:
        return False

