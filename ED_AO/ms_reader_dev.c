//--------------------------------------
//--- 010 Editor v3.2 Binary Template
//
// File: 碧之轨迹 MSFileTemplate.bt
// Author:acgngca
// Revision:2011-11-18 部分变量类型变动+统一
// Modify: JZS
// Purpose:查看编辑MSFile
// References:  htp://tieba.baidu.com/p/1117902374
//              http://tieba.baidu.com/p/596221801  18L 20L
//              http://tieba.baidu.com/p/708648905
//--------------------------------------

LittleEndian();

typedef struct 
{   
      ubyte		Time                <format = hex, name = "动机">;      
      ubyte		Probability                       <name = "概率">;
      ubyte		Target              <format = hex, name = "目标">;
      ubyte		DUMMY_STRUCT_01     <hidden = true>;        // 01
      ubyte   	AriaAnimation     	<format = hex, name = "蓄技动画代码">;        // 动画代码，在AS文件中定义。蓄魔法06、放魔法07、C技10-19，S技1A-1F
      ubyte   	CraftAnimation    	<format = hex, name = "放技动画代码">; 
      ushort		CraftIndex        								<name = "技能代码">;            // 技能代码，常规查t_magic._dt，自定义技能从E8 03开始
      int			TimeParameter1      							<name = "动机参数1">;
      int			TimeParameter2      							<name = "动机参数2">;
      int			TargetParameter1    							<name = "目标参数1">;
      int			TargetParameter2    							<name = "目标参数2">;
} AI_INFO   		 				<read = readCraftIndex,  comment = "[AI信息]">;

string readCraftIndex(AI_INFO &a)
{    
    if (a.CraftIndex >= 1000) {
        int i  = a.CraftIndex - 1000;
        if(monster_craft.Craft[i].CraftName != "")
            return monster_craft.Craft[i].CraftName;
        else {
        string  temp;
        SPrintf(temp,"技能[%d]",i);
        return  ConvertString(temp, CHARSET_UTF8 , CHARSET_CHINESE_S );
        }
    }
        string  temp;
        SPrintf(temp,"%d",a.CraftIndex);
        return  temp;
  
}



//属性
typedef enum <ubyte>
{   
    None,
    Soil,
    Water,
    Fire,
    Wind,
    Time,
    Space,
    Illusion
} ATTIBUTE  <read = readAttibute>;


string readAttibute(ATTIBUTE &a)
{
    string result;
    if(a == 0)          result = "无";
    else if(a == 1)     result = "地";
    else if(a == 2)     result = "水";
    else if(a == 3)     result = "火";
    else if(a == 4)     result = "风";
    else if(a == 5)     result = "时";
    else if(a == 6)     result = "空";
    else if(a == 7)     result = "幻";
    return ConvertString( result, CHARSET_UTF8 , CHARSET_CHINESE_S );
}




//技能范围
typedef ubyte Shape;



// 自定义技能格式
typedef struct  
{
	ushort	    AnimationType       <hidden = true,comment = "动画类型">;
    ubyte	    	Target              <comment = "发动对象">; 
    
    struct SPECIAL_EFFECT
    {
        ubyte   Must    :1          <name = "必定命中">;
        ubyte   NoBreak :1          <name = "驱动不被打断">;
        ubyte           :4;
        ubyte   NoReflect:1         <name = "不可被魔法反射">;
        ubyte             :1;
    }   SpecialEffect <name = "特效设置">;
	//ubyte	    	SpecialEffect				<comment = "01 强制命中 02 驱动时无法被打断">;     // 01 强制命中 02 驱动时无法被打断
	ATTIBUTE 		Attribute           <name = "技能属性">;				
	Shape	    	ShapeScope					<read = readShapeShope, comment = "技能范围">;   // 范围形状
	ubyte	    	Effect1             <name = "技能发动效果1">;
	ubyte	    	Effect2             <name = "技能发动效果2">;

	ubyte				DisplayIndex    <hidden = true>;		// 魔法列表中的显示先后，t_magic._dt中的才有效，ms文件中的一般为1或0。
	ubyte				RNG;
	ubyte				STCharge            <name = "咏唱ST">;
	ubyte				STCoolDown          <name = "硬直ST">;
	ushort			CP_EP               <name = "CP/EP消耗">;
	ushort			ScopeRadius         <name = "攻击范围半径">;		    // 半径

	short				Effect1Parameter    <name = "效果1参数(概率/幅度)">;
	ushort			Effect1Time         <name = "效果1持续回合数">;
	short				Effect2Parameter    <name = "效果2参数(概率/幅度)">;
	ushort			Effect2Time         <name = "效果2持续回合数">;

  string  		CraftName           <name = "技能名称">;          // max 32 bytes
	string  		CraftIntro          <name = "技能说明">;          // max 256 bytes

} CRAFT_INFO <read=readCraftName,write=writeCraftName>;

//技能范围
string readShapeShope(Shape s)
{   
   string result; 
   switch(s) {
    case  0:  result = "无";
                    break;  
    case  1:	result = "广域 单体";
                    break;
	  case  2:	result = "广域 目标指定 圆";         
                    break;
		case  3:	result = "狭域 目标指定 直线";          
                    break;
		case  4:	result = "狭域 单体";		         
                    break;                 //无位移，算距离时不加MOV。RNG为0时按人物RNG算，否则按技能指定RNG算，不加人物RNG。
		case  5:	result = "狭域 目标指定 圆";            
                    break;             //无位移，算距离时不加MOV。
		case  6:	result = "连锁战技专用";          
                    break;
		case 11:	result = "狭域 地点指定 圆";            
                    break;            //无位移，算距离时不加MOV。指定地点可以有人。
		case 12:	result = "远程技能用 狭域 地点指定 直线";   //comfirm!        
                    break;
		case 13:	result = "全屏";                  
                    break;
		case 14:	result = "自己中心 圆";             
                    break;
		case 15:	result = "广域 地点指定 圆"	;             
                    break;               
		case 17:	result = "近身技能用 狭域 地点指定 直线";	   //confirm!       
                    break;              
		case 18:	result = "地点指定圆，敌我不分";    
                    break;               //，1300为一格	//灭界，SC 零轨有，3rd未测试
		case 19:	result = "导力装甲/还原";	          
                    break;               //变身，即找个怪替换自己 //贝沃三段变身，替换成谁看战场设定
		case 50:	result = "移动/分裂/召唤 分身";     
                    break;
    default: 
    }
   return ConvertString(result, CHARSET_UTF8 , CHARSET_CHINESE_S );
}


string readCraftName(CRAFT_INFO &a)
{
    return  a.CraftName;
}

void writeCraftName(CRAFT_INFO &a, string s)
{
    // 复制用
}



typedef struct
{
	uint	Poison :1               <name = "毒">;
	uint	Frozen :1               <name = "冻结">;	            
	uint	Landification :1        <name = "石化">;	   
	uint	Sleep :1                <name = "睡眠">;
	           
	uint	DisableAtrs :1          <name = "封魔">;        
	uint	Dark :1                 <name = "黑暗">;	          
	uint	DisableCraft:1          <name = "封技">;	    
	uint	Chaos:1                 <name = "混乱">;
	           
	uint	Faint :1                <name = "气绝">;           
	uint	Kill :1                 <name = "即死">;	       
	uint	Burn :1                 <name = "炎伤">;	     
	uint	Angry :1                <name = "愤怒">;
	       
	uint	ArtsGuard  :1           <name = "魔法攻击保护">;	      
	uint	CraftGuard :1           <name = "物理攻击保护">;		      
	uint	MaxGuard  :1            <name = "墙">;         
	uint	Vanish :1               <name = "消失">;   
 
	uint	STR_UP_DOWN :1          <name = "攻击下降">;
    uint	DEF_UP_DOWN :1          <name = "防御下降">;
    uint    ATS_UP_DOWN :1          <name = "魔攻下降">;
	uint	ADF_UP_DOWN :1          <name = "魔防下降">;

	uint	DEX_UP_DOWN :1          <name = "命中下降">;
	uint	AGL_UP_DOWN :1          <name = "回避下降">;
	uint	MOV_UP_DOWN :1          <name = "移动下降">;
	uint	SPD_UP_DOWN :1          <name = "速度下降">;

	uint	HP_Recovery:1           <name = "HP 按回合回复">; 
	uint	CP_Recovery:1           <name = "CP 按回合回复">;  
	uint	Stealth:1               <name = "隐身">;     
	uint	AtrsRefelct:1           <name = "魔法反射">;         
 
	uint	BurningHeart:1          <name = "燃烧之心状态", comment = "全异常状态避免，n回合后气绝。包括vanish和fat，但fat的-20CP效果避免不了。">; 
	uint	CraftReflect:1          <name = "物理反射">;
	uint	FatSmall:1              <name = "变胖/变小/青椒">;  
	uint	Death:1                 <name = "死亡">;

} Resistance                    <name = "抗性" , comment = "1表示对该状态免疫，即不会具有此状态">;


typedef struct
{
    uint  Index    :20          <format=hex, comment = "索引号，除个位其余与文件名相同">;
    uint  FileType :12          <format=hex, comment = getFileType>;
    //  300:MS  data\battle\dat\ms12345.dat
    //  301:AS  data\battle\dat\as12345.dat
    //	302:BS	data\battle\dat\bs12345.dat
    //  310:SY  data\battle\symbol\sy12345.itp (SymbolTexture AT条头像)
    //  007:CH  data\chr\ch12345.itc    //人物素材，如在队时 站立移动时的形象
    //	008:CH	data\apl\ch12345.itc
    //  009:CH  data\monster\ch12345.itc//怪物素材，
} FileIndex                     <read = getFileIndex, comment = "文件索引">;

string getFileType(uint  FileType)
{   
    string  temp;
    switch (FileType) {
        case 0x300: temp = "MS";   break;
        case 0x301: temp = "AS";   break;
        case 0x302: temp = "BS";   break;
        case 0x310: temp = "SY";   break;
        case 0x007:
        case 0x008:
        case 0x009: temp = "CH";   break;
        default: SPrintf(temp,"%x",FileType);
    }
    return temp;   
}

string getFileIndex(FileIndex &a)
{   
    string type = getFileType(a.FileType);
    string index;
    SPrintf(index,"%.5x",a.Index);
    return  type + index;
}

typedef struct
{
	FileIndex			ASFileIndex <hidden = true, format=hex>;   // -301
	ushort				Level               <name = "等级">;
	uint					HPMax               <name = "最大HP">;
	uint					HP                  <name = "初始HP">;
	ushort				EPMax               <name = "最大EP">;
	ushort				EP                  <name = "初始EP">;
	ushort				CPMax               <name = "最大CP">;
	ushort				CP                  <name = "初始CP">;
	ushort				SPD                 <name = "速度">;
	ushort				MoveSPD             <name = "移动动作速度">;
	ushort				MOV                 <name = "移动">;                 
	ushort				STR                 <name = "攻击">; 
	ushort				DEF                 <name = "防御">; 	
	ushort				ATS                 <name = "魔攻">; 
	ushort				ADF                 <name = "魔防">; 
	ushort				DEX                 <name = "命中">; 
	ushort				AGL                 <name = "回避">; 
	ushort				RNG                 <name = "范围">; 
	ushort				MoveAfterAttack     <hidden = true,name = "被击退距离", comment = "一般为0，距离为1;设置过大会导致缓慢后退">;
	ushort				EXP                 <name = "经验值",     comment = "Target.Level - self.Level) * EXP">;
	ushort				Dummy               <hidden = true>;
} MONSTER_STATUS1;




typedef struct
{
  ubyte					Dummy1               <hidden = true>;
	ushort				AIType               <name = "AI类型">;                     
    // 00 敌方大多数
    // 01 NPC类AI 会走来走去但仍会发技
    // 02 ??    ms43300	
    // 04 ??    ms86300 青椒AI，无力地令人发狂？？？
    // AItype: 31 ms89000	HP: 60000	Lv: 120	
    // AItype: 30 ms89100	HP: 203210	Lv: 120	
    // 0A 
    // FF 由玩家控制
    ushort				Darkness           <hidden = true, name = "暗度", comment = "怪周围的暗度，正常1000">;
	ubyte					Dummy2[4]          <hidden=true>;   
    // 几乎都一样 10649609
    //ms02102	HP: 15360	Lv: 40	unknown: 144867337
    //ms08200	HP: 1430	Lv: 8	unknown: 144867337

	ubyte					Flags   <hidden = true>;                      // 10 敌方 40 己方 ...

    struct DEATH_FLAG
    {
      ubyte            :   1;
      ubyte   WinNeed  :   1   <name = "不参与战场胜利判定 ">;       
      ubyte   Leave    :   1   <name = "死后留在战场上    ">;
      ubyte   NoDead   :   1   <name = "不被打死          ">;
      ubyte            :   3;
      ubyte   NoShow   :   1   <name = "不显示在战场上    ">;
    } DeathFlags <format = hex, name = "死亡设置">;				                 
    // x02 不参与战场胜利判定 
    // x04 死后留在战场上
    // x08 不会被打死
    // x80 不显示在战场上

    struct ATTACK_FLAG
    {
        ubyte MoveDisable   :   1   <name = "不能移动">;    
        ubyte DrawDisable   :   1   <name = "不被击退">;
        ubyte               :   1;
        ubyte ATDelayDisable:   1   <name = "免疫AT延迟">;
        ubyte AttackDisable :   1   <name = "不能被攻击">;
        ubyte               :   1;
        ubyte SwimDisplay   :   1   <name = "死时浮动显示", hidden = true>;
        ubyte               :   1 ;
    }   AttackFlags    <format = hex, name = "打击设置">;       
  
	ubyte					DUMMY_STRUCT_04[4] <hidden=true>;   // 0或1 人物模型显示？
    
    struct OBJECT_FLAG
    {   
        ubyte HighLevelSpace    :   1 <name = "处于异空间", comment = "即是否开启时空幻有效率显示">;
        ubyte                   :   2;
        ubyte Human             :   1 <name = "为人类">;
        ubyte                   :   4;
    }   ObjectFlags     <format = hex, name = "属性设置">;  
    // 是否开启时空幻有效率显示，0/8 不开启 1/9 开启，应该是标志位，可能含有更多信息


	enum <ubyte> SEX
    {
        Male = 0,
        Female = 1
    }	Sex <hidden = true, name = "性别", read = readSex>;         


    uint				Size                <hidden = true, name = "占地大小", comment = "函数关系尚不明确">;         // 占地大小，1格400 高达1200
    //0-599             1 + 0   //400
    //600-999           1 + 1 * 4   //600 800
    //1000-1014         5 + 0   //1000
    //1015-1414         5 + 1 * 4   //1200
    //1415-1599         9 + 0   //1500
    //1600-1836         9 + 1 * 4   //1600
    //1837-             9 + 3 * 12
	uint				showEffectPosition_X         <hidden = true>;
    uint				showEffectPosition_Z         <hidden = true,name = "高度", comment = "选中目标时箭头离地高度，正常800">;   
    uint                showEffectPosition_Y         <hidden = true>;

    ubyte				ConditionDisplayPositionX         <hidden = true>; // 状态在头顶的显示位置 左右/前后/左右/未知 60/50/A0/B0 10/00/F0/FF
    ubyte               ConditionDisplayPositionZ         <hidden = true>; 
    ubyte               ConditionDisplayPositionY         <hidden = true>;                                             
    ubyte               ConditionDisplayPosition_unknown  <hidden = true>;
	
    FileIndex			SymbolTextureFileIndex <hidden = true, format=hex, name = "SYFileIndex", comment = "AT条头像文件索引">;

	Resistance		    resistance;                 // 异常状态抗性
    
    struct AttResistance {
        ushort  Resistance1 <name = "地">; 
        ushort  Resistance2 <name = "水">;
        ushort  Resistance3 <name = "火">;        
        ushort  Resistance4 <name = "风">;        
        ushort  Resistance5 <name = "时">;
        ushort  Resistance6 <name = "空">;
        ushort  Resistance7 <name = "幻">;
    }   attResistance <name = "属性有效率">;

    struct SEPITH {
        ubyte Sepith1 <name = "地">;
        ubyte Sepith2 <name = "水">;
        ubyte Sepith3 <name = "火">;
        ubyte Sepith4 <name = "风">;
        ubyte Sepith5 <name = "时">;
        ubyte Sepith6 <name = "空">;
        ubyte Sepith7 <name = "幻">;
    }   Sepith  <name = "掉落晶耀片数">;
   
	ushort				DropIndex1                  <name = "掉落物品1", comment = "物品代码">; 
	ushort				DropIndex2                  <name = "掉落物品2", comment = "物品代码">; 
	ubyte					DropProbability1            <name = "物品1掉落概率">;          
	ubyte					DropProbability2            <name = "物品2掉落概率">;

	struct	EQUIT {                    
        ushort          Weapon                        <name = "武器">;
        ushort          Armor                         <name = "服装">;
        ushort          Shoes                         <name = "鞋子">;
        ushort          Accessory                     <name = "饰品">;                     
        ushort          Accessory                     <name = "饰品">;   
    } Equit <name = "持有装备", comment = "物品代码">;    
               
	ushort				Orb[4]                      <name = "持有回路", comment = "物品代码">;                     
} MONSTER_STATUS2;

string readSex(SEX sex)
{
   string result; 
   if(sex == 1) result =  "女";
   else result=  "男/无性别";
   return ConvertString( result, CHARSET_UTF8 , CHARSET_CHINESE_S );
}

typedef struct
{
	AI_INFO			NormalAttack    <name = "普技AI">;         
    ////////////////////////////////////////////////////////////
	ubyte			ArtCount        <name = "魔技数", comment = "最多80个">;    
    if(ArtCount > 0) {
	    AI_INFO     ArtAI[ArtCount] <name = "魔技AI">;
    }
    ////////////////////////////////////////////////////////////
	ubyte			CraftCount      <name = "战技数", comment ="最多10个">;                 
    if(CraftCount > 0) {
	    AI_INFO     CraftAI[CraftCount] <name=  "战技AI">;
	}
    ///////////////////////////////////////////////////////////
	ubyte			SCraftCount     <name = "S技数", comment ="最多5 个">;                
    if(SCraftCount > 0){
	    AI_INFO     SCraftAI[SCraftCount]<name = "S技AI">;
	}
    ///////////////////////////////////////////////////////////
    ubyte           SupportCraftCount <name = "支援技数",comment = "最多3 个">;         // 支援战技, 最多3个
    if(SupportCraftCount > 0){
        AI_INFO     SupportCraftAI[SupportCraftCount]   <name = "支援技AI">;
	}
} MONSTER_AI;


typedef struct
{
	ubyte				CraftCount      <name = "技能数", comment = "自定义技能, 最多15个">;                 // ??
    if(CraftCount > 0)
	{
	    CRAFT_INFO	Craft[CraftCount] <name = "技能", optimize=false>;
	}
} MONSTER_CRAFT;


typedef struct
{
    ubyte   FleeType    <name     = "逃跑类型">;
    ubyte   FleeRate    <name     = "逃跑率">;
    ushort  FleePerametter  <name = "逃跑参数">;
}   FleeSettings <read = readFlee>;    

string readFlee(FleeSettings &s) {
    string temp;
    if(s.FleeType == 0) {
        temp = "不逃跑";
    }
    if(s.FleeType == 1) {
        SPrintf(temp,"当血量在%d%%以下时以%d%%概率逃跑", s.FleePerametter, s.FleeRate );
    }
    if(s.FleeType == 3) {
        SPrintf(temp,"每次行动以%d%%概率逃跑",s.FleeRate);
    } 
    return ConvertString( temp, CHARSET_UTF8 , CHARSET_CHINESE_S );
}

    MONSTER_STATUS1    monster_status1   <name    = "敌人数据Ⅰ">; 
    MONSTER_STATUS2    monster_status2   <name    = "敌人数据Ⅱ">;
    MONSTER_AI		     monster_ai        <name  = "敌人AI">;
    MONSTER_CRAFT	     monster_craft     <name  = "技能表">;
    FleeSettings	     fleeSettings       <name = "逃跑设定">;  															
// 逃跑参数 03320000 每次行动50%概率逃跑; 01320A00 血量10%以下50%概率逃跑
    string				 		 MonsterName       <name = "敌人名称">;
    string				 		 MonsterIntro      <name = "敌人介绍">;