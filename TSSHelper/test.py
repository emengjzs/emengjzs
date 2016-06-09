#-*- coding:utf-8 -*-
__author__ = 'jzs'

import requests

data = {
    'username'  : 'jzs12',
    'password'  : 'bleach318',
    'days'      : 90,
    'Remember'  : 'on',
    'Submit'    : 'Login'
}



s = requests.session()
print s.cookies
result = s.post('https://218.94.159.102/GlobalLogin/loginservlet',
                data=data,
                verify=False)
COURSE = 'http://218.94.159.102/tss/en/c0783/slide/index.html'
print s.headers
print s.get(COURSE).text