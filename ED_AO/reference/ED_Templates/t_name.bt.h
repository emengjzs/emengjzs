//--------------------------------------
//--- 010 Editor v3.2 Binary Template
//
// File: 零之轨迹/碧之轨迹 t_name._dt.h
// Author:acgngca
// Revision:
// Purpose:
// Readme:
//
//--------------------------------------

// 角色列表
//-----------------------------------------------------------------------------------------------------------
typedef struct(int parCHARACTER)	// 0x14 bytes
{
	if (parCHARACTER == 1)
	{
		ushort		CharacterIndex <fgcolor=cBlue>;
		ushort		addrCharacterName <hidden=true>;
		FileIndex	CHFileIndexStand <format=hex>;
		FileIndex	CHFileIndexMove <format=hex>;   
		FileIndex	MSFileIndex <format=hex>;
		FSkip(4);

		local uint	addrTemp = FTell();
		local uint	addrCharacterNameReal = addrCharacterName + addrT_name;
		FSeek(addrCharacterNameReal);
		string	CharacterName;
		FSeek(addrTemp);
	}
	else if (parCHARACTER == 0)
	{
		byte			MSFileIndex;	// 去掉 WARNING Line xx: Empty structure.
		local string	CharacterName = "nul \t";
	}
} CHARACTER <read=readCharacterName,write=writeCharacterName>;

string readCharacterName(CHARACTER &a)
{
	string temp;
	SPrintf(temp,"ms%08X  ",a.MSFileIndex);
	return StrDel(temp,2,3) + a.CharacterName;
}

void writeCharacterName(CHARACTER &a, string s)
{
	// 复制用
}

typedef struct
{
	local int	i = 0, j = 0, k = 0;
	local int	characterIndexMax = 999;
	local uint	addrTemp, addrK;

	while(1) 
	{
		addrTemp = FTell();
		addrK = addrTemp;
		k = ReadUShort(addrK);

		if (k > characterIndexMax || i > characterIndexMax)
		{
			break;
		}
		else if (k == i)
		{
			CHARACTER	character(1);
			i++;
		}
		else if (k < i)
		{
			Warning("Warning: Reverse index met at pos 0x%X, 0x%X.", addrTemp, addrK);
			Printf("Warning: Reverse index met at pos 0x%X, 0x%X.\n", addrTemp, addrK);
			FSkip(0x14);
		}
		else
		{
			for (j = i; j < k; j++)
			{
				FSeek(addrT_name);
				CHARACTER	character(0) <hidden=true>;
				i++;
			}
			FSeek(addrK);
			CHARACTER	character(1);
			i++;
		}
	}
} T_name <read=readT_name>;

string readT_name(T_name &a)
{
	return  "角色列表";
}