# -*- coding:utf-8-*-
__author__ = 'jzs'

root = u"D:\Downloads\星火英语大学英语6级词汇周计划的听力MP3\星火英语大学英语6级词汇周计划的听力MP3"

import os
import re

for path, category_list, file_list in os.walk(root):
    if file_list:
        for f in file_list:
            if 'lrc' in f:
                words = []
                data = open(path + u'\\' + f, 'r')
                for line in data:
                    r = re.search(r'[A-Za-z-]+', line)
                    if r:
                        words.append(r.group(0))
                data.close()
                new_f = open(path + u'\\' + f.replace('lrc', 'txt'), 'w')
                for w in words:
                    new_f.write(w + '\n')
                new_f.close()