//--------------------------------------
//--- 010 Editor v4.0.3 Binary Template
//
// File:
// Author:
// Revision:
// Purpose:
//--------------------------------------
#ifdef ED6_FC
#define ED6_ALL
#endif

#ifdef ED6_SC
#define ED6_ALL
#define ED6_SC_3RD
#endif

#ifdef ED6_3RD
#define ED6_ALL
#define ED6_SC_3RD
#endif

#ifdef ED_ZERO
#define ED_ZERO_AO
#endif

#ifdef ED_AO
#define ED_ZERO_AO
#endif


#ifdef ED_AO
	local int	MAGICSIZE = 28;
#else
	local int	MAGICSIZE = 32;
#endif

int DUMMY_STRUCT( int64 offset )
{
	return FSkip(offset);
}
///////////////////////////////////////////////////////////////////////////////////////////////////
typedef struct(int parArrayBytes)	// 字节数组，顺序显示用
{
	ubyte			arrayBytes[parArrayBytes];
	local int		i, arrayBytesSize;
	local string	stringTemp, stringSum = "";
	for (i = 0; i < parArrayBytes - 1; i++)
	{
		SPrintf(stringTemp, "%02X", arrayBytes[i]);
		stringSum = stringSum + stringTemp + " ";
	}
	SPrintf(stringTemp, "%02X", arrayBytes[i]);
	stringSum = stringSum + stringTemp;
	arrayBytesSize = parArrayBytes;
} ArrayBytes <read=readArrayBytes, write=writeArrayBytes, optimize=false>;

string readArrayBytes(ArrayBytes &a)
{
	return  a.stringSum;
}

void writeArrayBytes(ArrayBytes &a, string s)
{
	// 复制用
}


////////////////////////////////////////////////////////////////////////////////////////////////////
//if (ED6_NO & (ED6_FC | ED6_SC | ED6_3RD))
#ifdef ED6_ALL
	typedef struct  //DAT DIR文件索引
	{
		ushort  Index;
		ushort  DatNo <format=hex>;
		local string	display;
		SPrintf(display,"DT%02X  %04d", DatNo, Index);
	} FileIndex <read=readFileIndex,write=writeFileIndex>;

	string readFileIndex(FileIndex &a)
	{
		return	a.display;
	}

	void writeFileIndex(FileIndex &a, string s)
	{
		// 复制用
	}

#else
	typedef int	FileIndex;
#endif

////////////////////////////////////////////////////////////////////////////////////////////////////
// 	RESISTANCE/CONDITION
////////////////////////////////////////////////////////////////////////////////////////////////////
typedef union  //抗性
{
	int	condition <format = hex>;
	struct _condition_bits
	{
#ifdef ED6_FC
		uint	CONDITION_POISON				: 1;	// 毒
		uint	CONDITION_FREEZE            	: 1;	// 冻结 FROZEN
		uint	CONDITION_PETRIFY          		: 1;	// 石化 LANDIFICATION STONE
		uint	CONDITION_SLEEP           		: 1;	// 睡眠 SLEEPING
		uint	CONDITION_DISABLE_ARTS      	: 1;	// 封魔 MUTE
		uint	CONDITION_BLIND             	: 1;	// 黑暗 DARKNESS
		uint	CONDITION_DISABLE_CRAFT     	: 1;	// 封技 SEAL
		uint	CONDITION_CONFUSE          		: 1;	// 混乱 CHAOS
		uint	CONDITION_FAINT             	: 1;	// 气绝 STUN
		uint	CONDITION_SECOND_KILL			: 1;	// 即死
		uint	CONDITION_DEF_DOWN_FORCE		: 1;	// 绝对降防
		uint	CONDITION_RAGE					: 1;	// 愤怒
		uint	CONDITION_ARTS_GUARD			: 1;	// ArtsGuard
		uint	CONDITION_CRAFT_GUARD			: 1;	// CraftGuard
		uint	CONDITION_MOV_UP				: 1;
		uint	CONDITION_empty1				: 1;	// 无
		uint	CONDITION_STR_UP				: 1;
		uint	CONDITION_STR_DOWN				: 1;
		uint	CONDITION_DEF_UP				: 1;
		uint	CONDITION_DEF_DOWN				: 1;
		uint	CONDITION_SPD_UP				: 1;
		uint	CONDITION_SPD_DOWN				: 1;
		uint	CONDITION_DEX_UP				: 1;
		uint	CONDITION_DEX_DOWN				: 1;
		uint	CONDITION_AGL_UP				: 1;
		uint	CONDITION_AGL_DOWN				: 1;
		uint	CONDITION_MAX_GUARD				: 1;	// 墙 虚无领域 IMMUNE
		uint	CONDITION_VANISH_GUARD			: 1;	// 隐身GUARD
		uint	CONDITION_DEATH					: 1;	// 战斗不能 00 00 00 10
	//	uint	CONDITION_VANISH				: 1;	// Vanish
	//	uint	CONDITION_CONDITION_GUARD		: 1;	// 全状态抵抗
	//	uint	CONDITION_FAT					: 1;	// 变胖/变小
	//	uint	CONDITION_ATS_UP				: 1;
	//	uint	CONDITION_FORCE_SECOND_KILL		: 1;	// 死亡 FORCE_SECOND_KILL //only valid in ASfile
#endif

#ifdef ED6_SC
		uint	CONDITION_POISON				: 1;	// 毒
		uint	CONDITION_FREEZE            	: 1;	// 冻结 FROZEN
		uint	CONDITION_PETRIFY          		: 1;	// 石化 LANDIFICATION STONE
		uint	CONDITION_SLEEP           		: 1;	// 睡眠 SLEEPING
		uint	CONDITION_DISABLE_ARTS      	: 1;	// 封魔 MUTE
		uint	CONDITION_BLIND             	: 1;	// 黑暗 DARKNESS
		uint	CONDITION_DISABLE_CRAFT     	: 1;	// 封技 SEAL
		uint	CONDITION_CONFUSE          		: 1;	// 混乱 CHAOS
		uint	CONDITION_FAINT             	: 1;	// 气绝 STUN
		uint	CONDITION_SECOND_KILL			: 1;	// 即死
		uint	CONDITION_DEF_DOWN_FORCE		: 1;	// 绝对降防
		uint	CONDITION_RAGE					: 1;	// 愤怒
		uint	CONDITION_ARTS_GUARD			: 1;	// ArtsGuard
		uint	CONDITION_CRAFT_GUARD			: 1;	// CraftGuard
		uint	CONDITION_MOV_UP				: 1;
		uint	CONDITION_MOV_DOWN				: 1;
		uint	CONDITION_STR_UP				: 1;
		uint	CONDITION_STR_DOWN				: 1;
		uint	CONDITION_DEF_UP				: 1;
		uint	CONDITION_DEF_DOWN				: 1;
		uint	CONDITION_SPD_UP				: 1;
		uint	CONDITION_SPD_DOWN				: 1;
		uint	CONDITION_ADF_UP				: 1;
		uint	CONDITION_ADF_DOWN				: 1;
		uint	CONDITION_AGL_UP				: 1;
		uint	CONDITION_AGL_DOWN				: 1;
		uint	CONDITION_MAX_GUARD				: 1;	// 墙 虚无领域 IMMUNE
		uint	CONDITION_VANISH				: 1;	// Vanish
		uint	CONDITION_CONDITION_GUARD		: 1;	// 全状态抵抗
		uint	CONDITION_FAT					: 1;	// 变胖/变小
		uint	CONDITION_ATS_UP				: 1;
		uint	CONDITION_FORCE_SECOND_KILL		: 1;	// 死亡 FORCE_SECOND_KILL //only valid in ASfile
#endif

#ifdef ED6_3RD
		uint	CONDITION_POISON				: 1;	// 毒
		uint	CONDITION_FREEZE            	: 1;	// 冻结 FROZEN
		uint	CONDITION_PETRIFY          		: 1;	// 石化 LANDIFICATION STONE
		uint	CONDITION_SLEEP           		: 1;	// 睡眠 SLEEPING
		uint	CONDITION_DISABLE_ARTS      	: 1;	// 封魔 MUTE
		uint	CONDITION_BLIND             	: 1;	// 黑暗 DARKNESS
		uint	CONDITION_DISABLE_CRAFT     	: 1;	// 封技 SEAL
		uint	CONDITION_CONFUSE          		: 1;	// 混乱 CHAOS
		uint	CONDITION_FAINT             	: 1;	// 气绝 STUN
		uint	CONDITION_SECOND_KILL			: 1;	// 即死
		uint	CONDITION_DEF_DOWN_FORCE		: 1;	// 绝对降防
		uint	CONDITION_RAGE					: 1;	// 愤怒
		uint	CONDITION_ARTS_GUARD			: 1;	// ArtsGuard
		uint	CONDITION_CRAFT_GUARD			: 1;	// CraftGuard
		uint	CONDITION_MOV_UP				: 1;
		uint	CONDITION_MOV_DOWN				: 1;
		uint	CONDITION_STR_UP				: 1;
		uint	CONDITION_STR_DOWN				: 1;
		uint	CONDITION_DEF_UP				: 1;
		uint	CONDITION_DEF_DOWN				: 1;
		uint	CONDITION_SPD_UP				: 1;
		uint	CONDITION_SPD_DOWN				: 1;
		uint	CONDITION_ADF_UP				: 1;
		uint	CONDITION_ADF_DOWN				: 1;
		uint	CONDITION_AGL_UP				: 1;
		uint	CONDITION_AGL_DOWN				: 1;
		uint	CONDITION_MAX_GUARD				: 1;	// 墙 虚无领域 IMMUNE
		uint	CONDITION_VANISH				: 1;	// Vanish
		uint	CONDITION_CONDITION_GUARD		: 1;	// 全状态抵抗
		uint	CONDITION_FAT					: 1;	// 变胖/变小
		uint	CONDITION_ATS_UP				: 1;
		uint	CONDITION_FORCE_SECOND_KILL		: 1;	// 死亡 FORCE_SECOND_KILL //only valid in ASfile
#endif

#ifdef ED_ZERO
		uint	CONDITION_POISON            : 1;	// 毒
		uint	CONDITION_FREEZE            : 1;	// 冻结 FROZEN
		uint	CONDITION_PETRIFY           : 1;	// 石化 LANDIFICATION STONE
		uint	CONDITION_SLEEP             : 1;	// 睡眠 SLEEPING
		uint	CONDITION_DISABLE_ARTS      : 1;	// 封魔 MUTE
		uint	CONDITION_BLIND             : 1;	// 黑暗 DARKNESS
		uint	CONDITION_DISABLE_CRAFT     : 1;	// 封技 SEAL
		uint	CONDITION_CONFUSE           : 1;	// 混乱 CHAOS
		uint	CONDITION_FAINT             : 1;	// 气绝 STUN
		uint	CONDITION_SECOND_KILL       : 1;	// 即死
		uint	CONDITION_BURN              : 1;	// 炎伤
		uint	CONDITION_RAGE              : 1;	// 愤怒
		uint	CONDITION_ARTS_GUARD        : 1;	// ArtsGuard
		uint	CONDITION_CRAFT_GUARD       : 1;	// CraftGuard
		uint	CONDITION_MAX_GUARD         : 1;    // 墙 虚无领域 IMMUNE
		uint	CONDITION_VANISH            : 1;    // Vanish
		uint	CONDITION_STR_UP_DOWN       : 1;
		uint	CONDITION_DEF_UP_DOWN       : 1;
		uint	CONDITION_ATS_UP_DOWN       : 1;
		uint	CONDITION_ADF_UP_DOWN       : 1;
		uint	CONDITION_DEX_UP_DOWN       : 1;
		uint	CONDITION_AGL_UP_DOWN       : 1;
		uint	CONDITION_MOV_UP_DOWN       : 1;
		uint	CONDITION_SPD_UP_DOWN       : 1;
		uint	CONDITION_HP_RECOVERY       : 1;    // HP 按回合回复
		uint	CONDITION_CP_RECOVERY       : 1;    // CP 按回合回复
		uint	CONDITION_STEALTH           : 1;    // 隐身
		uint	CONDITION_ARTS_REFLECT      : 1;    // 魔法反射
		uint	CONDITION_Burning_Heart     : 1;    // 罗伊德燃烧之心第四个状态:全异常状态避免，n回合后气绝。包括vanish和fat，但fat的-20CP效果避免不了。
		uint	CONDITION_unknown1          : 1;
		uint	CONDITION_FAT               : 1;    // 变胖/变小
	//	uint	CONDITION_CONDITION_GUARD   : 1;    // 全状态抵抗
		uint	CONDITION_DEATH             : 1;    // 死亡 FORCE_SECOND_KILL //only valid in ASfile
#endif

#ifdef ED_AO
		uint	CONDITION_POISON            : 1;	// 毒
		uint	CONDITION_FREEZE            : 1;	// 冻结 FROZEN
		uint	CONDITION_PETRIFY           : 1;	// 石化 LANDIFICATION STONE
		uint	CONDITION_SLEEP             : 1;	// 睡眠 SLEEPING
		uint	CONDITION_DISABLE_ARTS      : 1;	// 封魔 MUTE
		uint	CONDITION_BLIND             : 1;	// 黑暗 DARKNESS
		uint	CONDITION_DISABLE_CRAFT     : 1;	// 封技 SEAL
		uint	CONDITION_CONFUSE           : 1;	// 混乱 CHAOS
		uint	CONDITION_FAINT             : 1;	// 气绝 STUN
		uint	CONDITION_SECOND_KILL       : 1;	// 即死
		uint	CONDITION_BURN              : 1;	// 炎伤
		//uint	CONDITION_RAGE              : 1;	// 愤怒
		//uint	CONDITION_ARTS_GUARD        : 1;	// ArtsGuard
		uint	CONDITION_unknown24			: 1;
		uint	CONDITION_unknown25			: 1;
		uint	CONDITION_CRAFT_GUARD       : 1;	// CraftGuard
		uint	CONDITION_MAX_GUARD         : 1;    // 墙 虚无领域 IMMUNE
		uint	CONDITION_VANISH            : 1;    // Vanish
		uint	CONDITION_STR_UP_DOWN       : 1;
		uint	CONDITION_DEF_UP_DOWN       : 1;
		uint	CONDITION_ATS_UP_DOWN       : 1;
		uint	CONDITION_ADF_UP_DOWN       : 1;
		uint	CONDITION_DEX_UP_DOWN       : 1;
		uint	CONDITION_AGL_UP_DOWN       : 1;
		uint	CONDITION_MOV_UP_DOWN       : 1;
		uint	CONDITION_SPD_UP_DOWN       : 1;
		uint	CONDITION_HP_RECOVERY       : 1;    // HP 按回合回复
		uint	CONDITION_CP_RECOVERY       : 1;    // CP 按回合回复
		uint	CONDITION_STEALTH           : 1;    // 隐身
		uint	CONDITION_ARTS_REFLECT      : 1;    // 魔法反射
		uint	CONDITION_Burning_Heart     : 1;    // 罗伊德燃烧之心第四个状态:全异常状态避免，n回合后气绝。包括vanish和fat，但fat的-20CP效果避免不了。
		uint	CONDITION_unknown1          : 1;
		uint	CONDITION_FAT               : 1;    // 变胖/变小
	//	uint	CONDITION_CONDITION_GUARD   : 1;    // 全状态抵抗
		uint	CONDITION_DEATH             : 1;    // 死亡 FORCE_SECOND_KILL //only valid in ASfile
#endif
	} conditon_bits;
} RESISTANCE <read=readRESISTANCE, write=writeRESISTANCE>;

string readRESISTANCE(RESISTANCE &a)
{
    string  temp;
    SPrintf(temp,"%08X",a.condition);
    return  temp;
}

void writeRESISTANCE(RESISTANCE &a, string s)
{
    SScanf(s, "%x", a.condition);
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// 	type
////////////////////////////////////////////////////////////////////////////////////////////////////
typedef union
{
	int	int_signed;
	uint int_unsigned;
	RESISTANCE condition;
} AI_PAR <read=readAI_PAR, write=writeAI_PAR>;

string readAI_PAR(AI_PAR &a)
{
    string  temp;
    SPrintf(temp,"%d",a.int_signed);
    return  temp;
}

void writeAI_PAR(AI_PAR &a, string s)
{
    SScanf(s, "%d", a.int_signed);
}

////////////////////////////////////////////////////////////////////////////////////////////////////
#ifdef ED6_FC
	typedef struct
	{
		USHORT				Level;
		USHORT				HPMax;
		USHORT				HP;

		USHORT				EPMax;
		USHORT				EP;
		USHORT				CP;
		int					EXP;    
		SHORT				STR;
		SHORT				DEF;
		SHORT				ATS;
		SHORT				ADF;
		SHORT				DEX;
		SHORT				AGL;
		SHORT				MOV;
		SHORT				SPD;
		USHORT				CPMAX;
		DUMMY_STRUCT(4);
		USHORT				RNG;
		DUMMY_STRUCT(4);
		RESISTANCE          condition;  // RESISTANCE类型，进程中有效
		USHORT              CharacterIndex;
		DUMMY_STRUCT(2);
	} ED6_CHARACTER_STATUS <optimize=true>;
	typedef ED6_CHARACTER_STATUS STATUS;
#endif

#ifdef ED6_SC_3RD
	typedef struct
	{
		UINT				Level;
		UINT				HPMax;
		UINT				HP;
		USHORT				EPMax;
		USHORT				EP;
		USHORT				CP;
		DUMMY_STRUCT(2);
		UINT				EXP;    
		SHORT				STR;
		SHORT				DEF;
		SHORT				ATS;
		SHORT				ADF;
		SHORT				DEX;
		SHORT				AGL;
		SHORT				MOV;
		SHORT				SPD;
		USHORT				CPMAX;
		DUMMY_STRUCT(4);
		USHORT				RNG;
		DUMMY_STRUCT(4);
		RESISTANCE			condition;  // RESISTANCE类型，进程中有效
		USHORT              CharacterIndex;
		DUMMY_STRUCT(2);
	} ED6_CHARACTER_STATUS <optimize=true>;
	typedef ED6_CHARACTER_STATUS STATUS;
#endif

#ifdef ED_ZERO_AO
	typedef struct
	{
		int					HPMax;
		int					HP;
		ushort				Level;
		ushort				EPMax;
		ushort				EP;
		ushort				CP;
		uint				EXP;    
		short				STR;
		short				DEF;
		short				ATS;
		short				ADF;
		short				DEX;
		short				AGL;
		short				MOV;
		short				SPD;
		short				DEXRate;
		short				AGLRate;
		ushort				CPMAX;
		ubyte				DUMMY_STRUCT_01[2]  <hidden=true>;
		ushort				RNG;		// 内存中改StatusBasic中的才有效
		ubyte				DUMMY_STRUCT_02[2]  <hidden=true>;
		RESISTANCE          condition;  // RESISTANCE类型，进程中有效; 战斗外包括存档中 DEATH有效
	} ED6_CHARACTER_STATUS;
	typedef ED6_CHARACTER_STATUS STATUS;
	typedef ED6_CHARACTER_STATUS ED7_CHARACTER_STATUS;
#endif
////////////////////////////////////////////////////////////////////////////////////////////////////
typedef struct  // 一组 20字节
{
	RESISTANCE	condition;
	ubyte       DUMMY_STRUCT_02[4] <hidden=true>;
	ushort		type <format=hex>;
	//ushort      Parameter;  // 0101 回合, 0201 次数, 0301 AT条动多少次, 0401 永久(石化)
	short       Effect; // 百分比数值
	ushort      ST;
	ubyte       DUMMY_STRUCT_03[6h] <hidden=true>;
} CONDITION <read=readCONDITION, write=writeCONDITION>;

string readCONDITION(CONDITION &a)
{
    string  temp;
	if (a.condition.condition != 0)
		SPrintf(temp,"%08X",a.condition.condition);
	else
		temp = "";
    return  temp;
}

void writeCONDITION(CONDITION &a, string s)
{
    //
}

// 逃跑参数
typedef ubyte FleeParameter[4] <read=readFleeParameter>;

string readFleeParameter(FleeParameter a)
{
    string  temp;
    SPrintf(temp,"%02X %02X %02X %02X",a[0],a[1],a[2],a[3]);
    return  temp;   
}

typedef struct  // Ai格式
{
    ubyte	Time <format=hex>;
	ubyte	Probability;
	ubyte	Target <format=hex>;
	ubyte	DUMMY_STRUCT_01;        // 01
    ubyte   CraftAnimation1;        // 动画代码，在AS文件中定义。蓄魔法06、放魔法07、C技10-19，S技1A-1F
    ubyte   CraftAnimation2;
	ushort	CraftIndex;             // 技能代码，常规查t_magic._dt，自定义技能从E8 03开始
	AI_PAR	TimeParameter1;
	AI_PAR	TimeParameter2;
	AI_PAR	TargetParameter1;
	AI_PAR	TargetParameter2;
} ED6_AI_INFO <read=readCraftIndex>;
typedef ED6_AI_INFO ED7_AI_INFO;

string readCraftIndex(ED6_AI_INFO &a)
{
    string  temp;
    SPrintf(temp,"%d",a.CraftIndex);
    return  temp;
}

typedef struct  // 自定义技能格式
{
	ushort	AnimationType;
	ubyte	Target; 
	ubyte	SpecialEffect;		// 01 强制命中 02 驱动时无法被打断
	ubyte	Att;				// 属性
	// 空轨FCSC		0无 1地 2水 3火 4风 5空 6幻 0时	// FCSC没有 时空幻有效率，所以无所谓
	// 空轨3rd		0无 1地 2水 3火 4风 5空 6幻 7时
	// 零轨碧轨		0无 1地 2水 3火 4风 5时 6空 7幻
	ubyte	ShapeScope;			// 范围形状
	ubyte	Effect1;
	ubyte	Effect2;
	if (MAGICSIZE == 32)
	{
		ushort	RNG;
		ushort	ScopeRadius;		// 半径
		ushort	STCharge;
		ushort	STCoolDown;
		ushort	CP_EP;
		ushort	DisplayIndex;		// 魔法列表中的显示先后，t_magic._dt中的才有效，ms文件中的一般为1或0。
	}
	else if (MAGICSIZE == 28)
	{
		ubyte	DisplayIndex;		// 魔法列表中的显示先后，t_magic._dt中的才有效，ms文件中的一般为1或0。
		ubyte	RNG;
		ubyte	STCharge;
		ubyte	STCoolDown;
		ushort	CP_EP;
		ushort	ScopeRadius;		// 半径
	}
	short	Effect1Parameter;
	ushort	Effect1ST;
	short	Effect2Parameter;
	ushort	Effect2ST;
	if (!IsProcess())
	{
		string  CraftName;          // max 32 bytes
		string  CraftIntro;         // max 256 bytes
	}
	else
	{
		byte   DUMMY_STRUCT_01[4] <hidden=true>;
		local int   addrTemp1 = FTell();
		local int   addrCraftName = addrCraftDefinition + MAGICSIZE*16 + 288*((addrTemp1-addrCraftDefinition)/(MAGICSIZE)-1) + 256;
		local int   addrCraftIntro = addrCraftDefinition + MAGICSIZE*16 + 288*((addrTemp1-addrCraftDefinition)/(MAGICSIZE)-1);
		FSeek(addrCraftName);
		string  CraftName;
		FSeek(addrCraftIntro);
		string  CraftIntro;
		FSeek(addrTemp1);
	}
} ED6_CRAFT_INFO <read=readCraftName,write=writeCraftName>;
typedef ED6_CRAFT_INFO ED7_CRAFT_INFO;

string readCraftName(ED6_CRAFT_INFO &a)
{
    return  a.CraftName;
}

void writeCraftName(ED6_CRAFT_INFO &a, string s)
{
    // 复制用
}

typedef struct  // 合在一块
{
	local int i;
	for (i = 0; i < 16; i++)
		ED6_CRAFT_INFO   CraftDefinition;    
} ED6_CRAFT_DEFINITION_GROUP; 
typedef ED6_CRAFT_DEFINITION_GROUP ED7_CRAFT_DEFINITION_GROUP;

// 最后一次(正在)使用的战技
typedef struct
{
	ushort	CraftIndex; 
	ubyte   CraftAnimation1;
	ubyte   CraftAnimation2;
} CraftLastUsed_INFO <read=readCraftLastUsedIndex>;

string readCraftLastUsedIndex(CraftLastUsed_INFO &a)
{
	string  temp;
	SPrintf(temp,"%d %d %d",a.CraftIndex, a.CraftAnimation1, a.CraftAnimation2);
	return  temp;
}

////////////////////////////////////////////////////////////////////////////////////////////////////
typedef struct
{
	ushort	Equip[5] <optimize=false>;
} EQUIP;

typedef struct
{
	ushort	SlotQuartz;
	ubyte	SlotLevel;
	FSkip(1);
} SLOT;

typedef struct
{
	SLOT	Slot[7] <optimize=false>;
} ORBMENT;

typedef struct
{
	ushort	Art[80] <optimize=false>;
} ARTS;

typedef struct
{
#ifdef ED_ZERO
	ushort	Craft[10] <optimize=false>;
#endif

#ifdef ED_AO
	ushort	Craft[15] <optimize=false>;
#endif
} CRAFT;

typedef struct
{
	ushort	SCraft[5] <optimize=false>;
} SCRAFT;

typedef struct	// 站位
{
	ubyte		x;	// 左下角为00，左右为x，上下为y
	ubyte       y;
} POSITION <read=readPosition>;

string readPosition(POSITION &a)
{
	string s;
	SPrintf(s,"%d %d", a.x, a.y);
	return  s;
};
////////////////////////////////////////////////////////////////////////////////////////////////////