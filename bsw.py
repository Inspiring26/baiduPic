# -*- coding:utf-8 -*-
import urllib2
import urllib
import re
from string import maketrans
import time
import os


def deCode(url):
	
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
	try:
		response = urllib2.urlopen(url, timeout = 10)
	except Exception as e:
		print 'respone: '+str(e)
	
	
	
	try:
		cont = response.read()
	except Exception as e:
		print 'cont: '+str(e)
		
	
	return cont


def getImgLinks(cont):
	try:
		picurl = re.findall('"objURL":"(.*?)"',cont)
	except Exception as e:
		print 'picurl: '+str(e)
		
	
	return picurl


def download(word,num):
	global i, number, n
	mark = 0

	url = more_urlss(word,num)

	try:
		cont = getHtml(url)
	except Exception as e:
		print 'cont: '+str(e)
		
	
	picurl = getImgLinks(cont)
	if len(picurl)<30:
		
		mark = 1
	
	for each in picurl:
		i = i + 1
		#try:
		each = deCode(each)
		
		#data=urllib.urlopen(each).read()
		try:
			
			res = urllib2.urlopen(each, timeout = 5)
		except Exception as e:
			print 'res: '+str(e)
			continue
		try:
			pic_data = res.read()
		except Exception as e:
			print 'pic_data: '+str(e)
			continue
	
		
		
		print str(len(pic_data)/1024)+'kb  '+str(time.strftime('%H:%M:%S',time.localtime(time.time())))
		if len(pic_data)>10000:
			number = number +1
			filename = os.getcwd() + '\\' + 'bsw' + '\\' + str(number) + '.jpg'
			try:
				f=open(filename, 'wb')
				f.write(pic_data)
				f.close()
			except Exception as e:
				print 'save_error: '+str(e)
				continue
			
		else:
			print 'too little,drop'

		
		
		pic_data = None
		print('process_number: '+str(i) +' ' + 'save_number: '+ str(number) + '  drop: ' + str(i-number))
		print('  ')
		print('  ')
		if mark == 1:
			print 'over...'
		if number==n:
			print 'over!'
			exit(0)	

if __name__ == '__main__':
	
	word = '刘亦菲古装剧照'
	
	n = 5




	os.mkdir('bsw')
	l = int(n//10+2)
	i = number = 0 
	
	for num in xrange(1, l):
		download(word, num)





