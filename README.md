<h1>简介</h1>

这是一个用Springboot搭建的象棋服务，目前主要完成的功能是提供了象棋的ai服务，也完成了线下对决服务，就是面对面不联网两个人用一部平板或者手机下棋，至于联机由于时间的原因，就没做啦>,<

使用了CocosCreater游戏引擎做成的微信小游戏前端（前端地址： https://github.com/18216499322/chess），

（由于我的阿里云服务到期了，所以看不了线上运行的效果了T~T）

要是对象棋的AI这块感兴趣的话也可以下载研究一下哈，AI这块和服务这块是通过一个接口来链接的，简单来说，就是下载之后，直接把接口以上的代码单独分离出来，然后直接调用接口已经实现的方法就可以了（成功的话点个小星星鼓励一下下哈，开发不易）

开发小组分工：

二峰（后端，整合AI服务，联机进度（50%...））联系QQ：3208027182

老洪（AI开发）

尚杰（前端）

演示效果如下：

<img src="https://github.com/ErfengV/ChessServer/blob/master/doc/wps1.png" alt="img" style="zoom: 50%;" />



<img src="https://github.com/ErfengV/ChessServer/blob/master/doc/%E5%AE%9E%E7%8E%B0AI%E8%81%94%E6%9C%BA.jpg" alt="实现AI联机" style="zoom: 50%;" />



<h1>其他资料</h1>

##### 一、**棋子号**

AI是黑方		玩家是红方	不能切换

前端棋盘

window.gameMap = [

  ['r_c_l','r_m_l','r_x_l','r_s_l','r_j','r_s_r','r_x_r','r_m_r','r_c_r'],

  [0,0,0,0,0,0,0,0,0],

  [0,'r_p_l',0,0,0,0,0,'r_p_r',0],

  ['r_z_1',0,'r_z_2',0,'r_z_3',0,'r_z_4',0,'r_z_5'],

  [0,0,0,0,0,0,0,0,0],

  [0,0,0,0,0,0,0,0,0],

  ['b_z_1',0,'b_z_2',0,'b_z_3',0,'b_z_4',0,'b_z_5'],

  [0,'b_p_l',0,0,0,0,0,'b_p_r',0],

  [0,0,0,0,0,0,0,0,0],

  ['b_c_l','b_m_l','b_x_l','b_s_l','b_j','b_s_r','b_x_r','b_m_r','b_c_r']

]

棋子信息

r_c_l 红车左 坐标索引号 [0][0]

r_c_r红车右 坐标索引号 [0][8]

b_c_l黑车左 坐标索引号 [9][0]

b_c_r黑车右 坐标索引号 [9][8]

协议规范

num<>user<>idx,x,y	房间号<>红方还是黑方<>棋子名,棋子坐标x,y	

示例：341234324<>black<>r_c_l,0,1		

房间号由前端按时间戳来生成

##### 二、**后端返回的数据格式，及部分状态码**

后端返回给前端我们一般用JSON体方式，定义如下：

{
	#返回状态码
	code:integer,		
	#返回信息描述
	message:string,
	#返回值
	data:object
}

部分状态码：

//参数错误：1001-1999

PARAM_IS_INVALID(1001,"参数无效"),

PARAM_IS_BLANK(1002,"参数为空"),

PARAM_TYPE_BIND_ERROR(1003,"参数类型错误"),

PARAM_NOT_COMPLETE(1004,"参数缺失"),

//用户错误：2001-2999

USER_NOT_LOGGED_IN(2001,"用户未登录，访问的路径需要验证，请登录"),

//协议错误：3001-3999

PROTOCOL_IS_INVALID(3001,"协议无效"),

PARAM_ERROR(3002,"协议内容错误"),

//成功状态码

SUCCESS(1,"成功");

**三、AI的UML类图**

<img src="https://github.com/ErfengV/ChessServer/blob/master/doc/wps2.jpg" alt="img" style="zoom: 55%;" />
