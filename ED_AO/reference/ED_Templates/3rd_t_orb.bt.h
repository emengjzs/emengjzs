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
	ArrayBytes	att(9);
	ubyte		lineNum;
	for (j = 0; j < lineNum; j++)
		ArrayBytes	line(8);
	ArrayBytes	end(2);
} Orb_Set;
typedef struct
{
	local uint	addrThis = FTell();
	
	local int	i = 0, j = 0, k = 0;	
	local int	characterTotalNum = 0 ;
	if (ReadUShort(ReadUShort(FTell()) + addrThis - 2) == 0xFFFF)
	{
		while(1)
		{
			if (ReadUShort(FTell()) ==  0xFFFF)
				break;
			characterTotalNum++;
			FSkip(2);
		}
	}
	else
	{
		characterTotalNum = ReadUShort(addrThis) / 2;
	}
	FSeek(addrThis);
	ushort		addr[characterTotalNum];
	
	for(i = 0; i < characterTotalNum; i++)
	{
		FSeek(addrThis + addr[i]);		
		Orb_Set	orb_set;
	}	
/*
	for(i = 0; i < characterTotalNum - 1; i++)
	{
		FSeek(addrThis + addr[i]);		
		ArrayBytes	orb_set(addr[i+1] - addr[i]);
	}
	ArrayBytes	orb_set(0x14);
*/	
} T_orb <read=readT_orb>;

string readT_orb(T_orb &a)
{
	string s;
	SPrintf(s, "导力器设定 %d人", a.characterTotalNum);
	return  s;
}