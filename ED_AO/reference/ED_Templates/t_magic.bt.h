//--------------------------------------
//--- 010 Editor v3.2 Binary Template
//
// File: 空之轨迹/零之轨迹/碧之轨迹 t_magic._dt Template
// Author:acgngca
// Revision:2011-11-18
// Purpose:查看、修改魔法战技效果
// Readme:任何改变文件大小的操作均不可以进行，除非你已经明晰本文件的格式
//
//--------------------------------------

// 魔法战技定义 部分
//-----------------------------------------------------------------------------------------------------------
typedef struct(int parMAGIC)
{
	if (parMAGIC == 1)
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
			ushort	DisplayIndex;		// 魔法列表中的显示先后，t_magic._dt中的才有效
		}
		else if (MAGICSIZE == 28)
		{
			ubyte	DisplayIndex;		// 魔法列表中的显示先后，t_magic._dt中的才有效
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

		local uint  addrTemp = FTell();

		ushort  addrMagicName <hidden=true>;	// 魔法名 地址
		ushort  addrMagicIntro <hidden=true>;	// 魔法说明 地址
		local uint addrMagicNameReal = addrMagicName + addrT_magic;
		local uint addrMagicIntroReal = addrMagicIntro + addrT_magic;
		if (addrMagicName != 0)
		{
			FSeek(addrMagicNameReal);		// 跳转
			string  MagicName;
			FSeek(addrMagicIntroReal);		// 跳转
			string	MagicIntro;
		}
		else
		{
			local string	MagicName = "nul \t";
		}
/* 内存用
		// 魔法属性值
		if (i >=10 && i <= 134)	// 不准确，逆行。 魔法列表的生成是直接去查t_magqrt, 只要MagicIndex对的上且属性值够就会添加, 不管是不是Art有没有定义. 搜索范围为(不对)max(134?, ReadUShort(0)/2).
		////if ((i >0)  && (i < ReadUShort(addrT_magqrt)/2) && (ReadUShort(ReadUShort(i*2 + addrT_magqrt) + addrT_magqrt) == i))
		{
			FSeek(ReadUShort(i*2 + addrT_magqrt) + addrT_magqrt);
			ushort	MagicIndex <hidden=true>;
			ushort	MagicElementalValue[7];
		}
*/
		FSeek(addrTemp);
	}
	else if (parMAGIC == 0)
	{
		ubyte 			DUMMY_STRUCT_01;	// 去掉 WARNING Line xx: Empty structure.
		local string	MagicName = "nul \t";
	}
} MAGIC <read=readMagicName,write=writeMagicName>;

string readMagicName(MAGIC &a)
{
	return	a.MagicName;
}

void writeMagicName(MAGIC &a, string s)
{
	// 复制用
}

typedef struct
{
	// 魔法个数
	if (ReadUShort(addrT_magic) == 0x72F0)
	{
		local ushort	TOTAL = 373;	// 整合补丁特例
	}
	else if (ReadUShort(addrT_magic) < ReadUShort(addrT_magic + 2))
	{
		local ushort	TOTAL = ReadUShort(addrT_magic)/2;	// 零轨350 碧轨433
	}

	// 魔法条长度 碧轨24+4 空轨零轨28+4
	if (!exists(MAGICSIZE))
	{
		if (ReadUShort(addrT_magic) == 0x362)
		{
			local ushort	MAGICSIZE = 28;
		}
		else
		{
			local ushort	MAGICSIZE = 32;
		}
	}	

	local int		i;
	ushort			addr[TOTAL]  <hidden=true>;	// 魔法条 地址

	for (i = 0; i < TOTAL-1; i++)
	{
		if (addr[i+1] - addr[i] >= MAGICSIZE)
		{
			FSeek(addr[i] + addrT_magic);	// 跳转
			MAGIC	magic(1);
		}
		else
		{
			FSeek(addrT_magic);
			MAGIC	magic(0) <hidden=true>;	// 隐藏无效魔法
		}
	}

	// 解决最后一个
	if (((magic[0].addrMagicName > addr[TOTAL-1]) && (magic[0].addrMagicName - addr[TOTAL-1] >= MAGICSIZE)) ||
		((magic[0].addrMagicName < addr[TOTAL-1]) && (FileSize() - addr[TOTAL-1] >= MAGICSIZE)))
	{
		FSeek(addr[TOTAL-1] + addrT_magic);
		MAGIC	magic(1);
	}
	else//// if (addr[i] == addr[i-1])
	{
		FSeek(addrT_magic);
		MAGIC	magic(0);
	}
} T_magic <read=readT_magic>;

string readT_magic(T_magic &a)
{
	return  "魔法战技定义";
}
