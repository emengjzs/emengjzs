//--------------------------------------
//--- 010 Editor v3.2 Binary Template
//
// File:
// Author:
// Revision:
// Purpose:
//--------------------------------------

typedef struct
{
	ushort				Level;
	ushort				HP;
	ushort				EP; 
	ushort				STR;
	ushort				DEF;
	ushort				ATS;
	ushort				ADF;
	ushort				DEX;
	ushort				AGL;
	ushort				MOV;
	ushort				SPD;
} STATUS_SET <read=readSTATUS_SET,write=writeSTATUS_SET>;

string readSTATUS_SET(STATUS_SET &a)
{
	string	s;
	SPrintf(s, "lv.%d", a.Level);
	return	s;
}

void writeSTATUS_SET(STATUS_SET &a, string s)
{
	// ������
}

typedef struct
{
	for (j = levelMin; j <= levelMax; j++)
	{
		STATUS_SET	status_lv;
	}
} STATUS_SET_GROUP;

typedef struct
{
	local uint	addrT_statusStart = FTell();
	local int	characterTotalNum = ReadUShort(FTell()) / 2;
	ushort		addr[characterTotalNum];
	local int	levelMin = ReadUShort(addrT_statusStart + addr[0]);
	//local int	levelMax = ReadUShort(addrT_statusStart + addr[1] - 0x16);	
	local int	levelMax = levelMin;
	local int	i = 0, j = 0, k = 0;
	
	FSeek(addrT_statusStart + addr[0]);
	while(1)
	{
		if(ReadUShort(FTell()) != levelMax)
		{
			levelMax--;
			break;
		}
		FSkip(0x16);
		levelMax++;
	}
	
	for(i = 0; i < characterTotalNum; i++)
	{
		FSeek(addrT_statusStart + addr[i]);		
		STATUS_SET_GROUP	status_set;
	}
	
	if (FileSize() - FTell() >= 0xCB8 && ReadUInt(FTell()) == 0xB60003)
	{
		STATUS_SET_GROUP	status_set;
	}
} T_status <read=readT_status>;

string readT_status(T_status &a)
{
	string s;
	SPrintf(s, "����ֵ�趨 %d�� %d-%d��", a.characterTotalNum, a.levelMin, a.levelMax);
	return  s;
}