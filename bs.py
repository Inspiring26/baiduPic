# -*- coding:utf-8 -*-
#Jun 26 添加判断获取超时机制,定为v1.3版吧
#Jun 26 pm 修复重要错误：pic_data = res.read()超时终止。v1.4
#Jun 26 midnight 总算体会到了，写程序要多用try...expect了。版本v1.5
#v1.5 关了其他程序，好好测试v1.5，先测能不能到10万，再测有没有尽头，测尽头可以找个小众的资源
#版本v1.6 新功能：1.提取关键字首字母2.创建首字母名的文件夹。其他：夜里测试v1.5爬取十万图片成功了！！
import urllib2
import urllib
import re
from string import maketrans
import time
import os


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
	try:
		response = urllib2.urlopen(url, timeout = 10)
	except Exception as e:
		print 'respone: '+str(e)
	
	
	#当程序停住的时候，我按ctrl+c，也提示这一句的问题，当时爬了30655个图片
	#也用一下try
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
	#虽然这里不常出错，但是我当程序停住的时候，我按ctrl+c，提示这里有问题
	try:
		cont = getHtml(url)
	except Exception as e:
		print 'cont: '+str(e)
		
	
	picurl = getImgLinks(cont)
	if len(picurl)<30:
		print 'len(picurl)小于30，只有'+str(len(picurl))+'个'
		mark = 1
	
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
			print 'res: '+str(e)
			continue
		try:
			pic_data = res.read()
		except Exception as e:
			print 'pic_data: '+str(e)
			continue
	
		
		#添加时间显示，如果卡住了，可以知道卡多久了。
		print str(len(pic_data)/1024)+'kb  '+str(time.strftime('%H:%M:%S',time.localtime(time.time())))
		if len(pic_data)>10000:
			number = number +1
			try:
				#f=open('./py/'+str(word)+str(number)+'.jpg', 'w+')
				f=open(r'./' + b + r'/' +str(word)+str(number)+'.jpg', 'w+')
				f.write(pic_data)
				f.close()
			except Exception as e:
				print 'save_error: '+str(e)
				continue
			
		else:
			print '小于10k，丢弃图片'

		#except:
		#	print('获取失败')
		
		pic_data = None
		print('任务编号： '+str(i)+'   '+'图片编号： '+str(number)+'   '+'无法获取个数：'+str(i-number))
		print('  ')
		print('  ')
		if mark == 1:
			print '一页不足30个图片，到尽头了，即将终止'
		if number==n:
			print '程序完成!'
			exit(0)	




#提取首字母模块
def multi_get_letter(str_input): 
  if isinstance(str_input, unicode): 
    unicode_str = str_input 
  else: 
    try: 
      unicode_str = str_input.decode('utf8') 
    except: 
      try: 
        unicode_str = str_input.decode('gbk') 
      except: 
        print 'unknown coding' 
        return 
  return_list = [] 
  for one_unicode in unicode_str: 
    return_list.append(single_get_first(one_unicode)) 
  return return_list 
def single_get_first(unicode1): 
  str1 = unicode1.encode('gbk') 
  try:     
    ord(str1) 
    return str1 
  except: 
    asc = ord(str1[0]) * 256 + ord(str1[1]) - 65536 
    if asc >= -20319 and asc <= -20284: 
      return 'a' 
    if asc >= -20283 and asc <= -19776: 
      return 'b' 
    if asc >= -19775 and asc <= -19219: 
      return 'c' 
    if asc >= -19218 and asc <= -18711: 
      return 'd' 
    if asc >= -18710 and asc <= -18527: 
      return 'e' 
    if asc >= -18526 and asc <= -18240: 
      return 'f' 
    if asc >= -18239 and asc <= -17923: 
      return 'g' 
    if asc >= -17922 and asc <= -17418: 
      return 'h' 
    if asc >= -17417 and asc <= -16475: 
      return 'j' 
    if asc >= -16474 and asc <= -16213: 
      return 'k' 
    if asc >= -16212 and asc <= -15641: 
      return 'l' 
    if asc >= -15640 and asc <= -15166: 
      return 'm' 
    if asc >= -15165 and asc <= -14923: 
      return 'n' 
    if asc >= -14922 and asc <= -14915: 
      return 'o' 
    if asc >= -14914 and asc <= -14631: 
      return 'p' 
    if asc >= -14630 and asc <= -14150: 
      return 'q' 
    if asc >= -14149 and asc <= -14091: 
      return 'r' 
    if asc >= -14090 and asc <= -13119: 
      return 's' 
    if asc >= -13118 and asc <= -12839: 
      return 't' 
    if asc >= -12838 and asc <= -12557: 
      return 'w' 
    if asc >= -12556 and asc <= -11848: 
      return 'x' 
    if asc >= -11847 and asc <= -11056: 
      return 'y' 
    if asc >= -11055 and asc <= -10247: 
      return 'z' 
    return '' 
def translate(str_input): 
  a = multi_get_letter(str_input) 
  b = '' 
  for i in a: 
    b= b+i 
  return b 


if __name__ == '__main__':
#可以修改程序使之能够指定名字，指定爬取个数。
	word = '刘亦菲古装剧照'
	#指定个数n
	n = 10



	b = translate(word)
	print b
	os.mkdir(b)
	#更高端一点可以设置50%的失败率上限,不过有个问题，如果是数量比较小的话这个百分比就没意义了
	l = int(n//10+2)
	i = number = 0 
	#多变量赋值用等号连接
	#for i in range (1, 5)	结果是1，2，3，4
	#记住3才是2
	for num in xrange(1, l):
		download(word, num)





