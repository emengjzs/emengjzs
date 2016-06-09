__author__ = 'jzs'
# encoding=utf-8
import urllib2
import urllib
import cookielib
import copy


def renrenBrower(url):
    # 登陆页面，可以通过抓包工具分析获得，如fiddler，wireshark
    import tss
    import test

    login_page = tss.LOGIN_PAGE
    try:
        cookie_support = urllib2.HTTPCookieProcessor(cookielib.CookieJar())
        opener = urllib2.build_opener(cookie_support, urllib2.HTTPHandler)
        urllib2.install_opener(opener)
        #获得一个cookieJar实例
        #cj = cookielib.CookieJar()
        #cookieJar作为参数，获得一个opener的实例
        #opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
        #伪装成一个正常的浏览器，避免有些web服务器拒绝访问。
        opener.addheaders = [('User-agent', 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)'),
                             ('Accept-Encoding', 'gzip, deflate'),
                             ('Connection', 'Keep-Alive'),
                             ('DNT', '1')
        ]
        #生成Post数据，含有登陆用户名密码。
        data = urllib.urlencode(test.data)
        #以post的方法访问登陆页面，访问之后cookieJar会自定保存cookie
        #print cj.__str__()
        (opener.open(login_page, data)).read()
        #以带cookie的方式访问页面
        #print cj.__str__()
        opener.addheaders = [('User-agent', 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)'),
                             ('Accept-Encoding', 'gzip, deflate'),
                             ('Connection', 'Keep-Alive'),
                             ('DNT', '1')
        ]
        urllib2.install_opener(new_opener)
        op = opener.open(url)
        #print cj.__str__()
        #读取页面源码
        data = op.read()
        return data
    except Exception, e:
        print str(e)


print renrenBrower('http://218.94.159.102/tss/en/c0783/slide/index.html')