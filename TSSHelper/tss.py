# -*- coding:utf-8 -*-
import pycurl
import urllib


class User:
    name = ''
    password = ''

    def __init__(self, n, p):
        self.name = n
        self.password = p

    def login_ftp(self, ftp):
        ftp.login(self.name, self.password)


LOGIN_PAGE = 'https://218.94.159.102/GlobalLogin/loginservlet'
LOGIN_PAGE = 'http://jwas3.nju.edu.cn:8080/jiaowu/login.do'
MY_COURSE_PAGE = 'https://218.94.159.102/tss/en/home/mycourse.html'
COURSE = 'http://218.94.159.102/tss/en/home/courselist.html'#tss/en/c0783/slide/index.html'
# MY_COURSE_PAGE = ';'.join([MY_COURSE_PAGE, 'jsessionid=B49A7EE1DAE4EF2B50395894A99A5498'])
data = {
    'userName'  : '121250063',
    'password'  : '120657'
}


def login():
    c = pycurl.Curl()


    c.setopt(pycurl.SSL_VERIFYPEER, 0)  # 忽略证书验证
    c.setopt(pycurl.SSL_VERIFYHOST, 0)

    import StringIO
    b = StringIO.StringIO()
    headstr = StringIO.StringIO()
    c.setopt(pycurl.WRITEFUNCTION, b.write)
    c.setopt(pycurl.HEADERFUNCTION, headstr.write) #调用header()函数来输出返回的信息

    c.setopt(pycurl.HEADER, 1)
    c.setopt(pycurl.URL, LOGIN_PAGE)
    #c.setopt(pycurl.COOKIEFILE, '_result.txt')
    c.setopt(pycurl.COOKIEJAR, 'result.txt')
    c.setopt(pycurl.POST, 1)
    c.setopt(c.POSTFIELDS, urllib.urlencode(data))
    c.perform()

    print str(b.getvalue())
    import re
    print headstr.getvalue()
    #r = re.search(r'(JSESSIONID=\w{32})', str(headstr.getvalue()))
    #tail =  str(r.groups()[0]).replace('JSESSIONID', '?SIGlobalLogin')
    #print tail
    ##c.setopt(pycurl.URL, COURSE + tail)
    #c.setopt(pycurl.HTTPGET, 1)
    #c.perform()
    #print str(b.getvalue()).decode('gbk')



def analysis(html):
    from BeautifulSoup import BeautifulSoup

    s = BeautifulSoup(html)
    print s.find('table')


login()
