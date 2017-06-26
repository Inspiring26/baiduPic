# -*- coding:utf-8 -*-
#Jun 26 添加判断获取超时机制,定为v1.3版吧
import urllib2
import urllib
import re
from string import maketrans
import time


def deCode(url):
	#字典
	str_table = {
	'_z2C$q': ':',
	'_z&e3B': '.',
	'AzdH3F': '/'
	}
	intab="wkv1ju2it3hs4g5rq6fp7eo8dn9cm0bla"
	outtab="abcdefghijklmnopqrstuvw1234567890"
	trantab = maketrans(intab, outtab)

	for key, value in str_table.items():
		url = url.replace(key, value)

	d = url.translate(trantab)
	return d


def more_urlss(word,num):
	word = urllib.quote(word)
	url = r'https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord='+word+r'&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word='+word+r'&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn='+str(num*30)+r'&rn=30'
	return url

def getHtml(url):
	response = urllib2.urlopen(url)
	cont = response.read()
	return cont


def getImgLinks(cont):
	picurl = re.findall('"objURL":"(.*?)"',cont)
	return picurl


def download(word,num):
	global i, number, n

	url = more_urlss(word,num)
	cont = getHtml(url)
	picurl = getImgLinks(cont)
	
	for each in picurl:
		i = i + 1
		#try:
		each = deCode(each)
		#给urlopen加上timeout,防止程序卡死在read()方法里
		#data=urllib.urlopen(each).read()
		try:
			#一开始用的res = urllib.urlopen(each, timeout = 5)一直报错，提示没有timeout参数
			res = urllib2.urlopen(each, timeout = 5)
		except Exception as e:
			print e
			continue
		
		data = res.read()
		#添加时间显示，如果卡住了，可以知道卡多久了。
		print str(len(data)/1024)+'kb  '+str(time.strftime('%H:%M:%S',time.localtime(time.time())))
		if len(data)>10000:
			number = number +1
			f=open('./py/'+str(word)+str(number)+'.jpg', 'w+')
			f.write(data)
			f.close()
		else:
			print '小于10k，丢弃图片'

		#except:
		#	print('获取失败')
		
		data=None
		print('任务编号： '+str(i)+'   '+'图片编号： '+str(number)+'   '+'无法获取个数：'+str(i-number))
		print('  ')
		print('  ')
		if number==n:
				exit(0)	

if __name__ == '__main__':
	#可以修改程序使之能够指定名字，指定爬取个数。
	word = '刘亦菲古装剧照'
	#指定个数n
	n = 20




	#更高端一点可以设置50%的失败率上限,不过有个问题，如果是数量比较小的话这个百分比就没意义了
	l = int(n//30*2+2)
	i = number = 0 
	#多变量赋值用等号连接
	#for i in range (1, 5)	结果是1，2，3，4
	#记住3才是2
	for j in xrange(1,l):
		download(word, j)




