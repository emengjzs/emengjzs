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
	local int	areaScenaTotalNum = 0;
	
	ushort	x;	//从左上 到 右下
	ushort	y;
	ushort	areaNameIndex;
	ushort	areaNameColor;
	ushort	areaNameDisplayOffsetY;
	ushort	appearTime;
	
	if (areaNameIndex != 0)
	{
		areaWithNameTotalNum++;
	}
	
	while (ReadInt(FTell()) != 0 && areaScenaTotalNum < 0x96)
	{
		 int	scena <format=hex>;
		 areaScenaTotalNum++;
		 scenaTotalNum++;
	}
	FSkip(4);
	
	local string display;
	if (areaNameIndex != 0)
	{
		SPrintf(display, "%03d %03d %03d %03d 0x%03X", x, y, areaNameIndex, areaScenaTotalNum, appearTime);
	}
	else
	{
		display = "";
	}
} Area_Set <read=readArea_Set>;

string readArea_Set(Area_Set &a)
{
	return	a.display;
}

typedef struct
{
	local uint	addrThis = FTell();
	
	local int	i = 0, j = 0, k = 0;	
	local int	areaTotalNum = 0;
	local int	scenaTotalNum = 0;	
	local int	areaWithNameTotalNum = 0;
	while (ReadInt(FTell()) != 0 && areaTotalNum < 0x96)
	{
		Area_Set	area;
		areaTotalNum++;
	}
/*	
	// 导出scena列表用
	FSeek(addrThis);
	while (ReadInt(FTell()) != 0 && areaTotalNum < 0x96)
	{
		FSkip(12);
		while (ReadInt(FTell()) != 0)
		{
			 int	scena <format=hex>;
		}
		FSkip(4);		
	}
*/	
} T_world <read=readT_world>;

string readT_world(T_world &a)
{
	string s;
	SPrintf(s, "世界地图地点显示 %d项", a.areaWithNameTotalNum);
	return  s;
}