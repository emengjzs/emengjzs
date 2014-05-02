//--------------------------------------
//--- 010 Editor v3.2 Binary Template
//
// File:
// Author:
// Revision:
// Purpose:
//--------------------------------------

typedef struct  //�з�վλ������
{
	ubyte		x;  	//���½�Ϊ00������Ϊx������Ϊy
	ubyte       y;
	ushort		degree; //����Ϊ0�㣬����Ϊ90�㣬�Դ����ơ�
} EnemyPosition <read=readEnemyPosition>;

string readEnemyPosition(EnemyPosition &a)
{
	string s;
	SPrintf(s,"%02d %02d %03d��", a.x, a.y, a.degree);
	return  s;
};

typedef struct
{
	EnemyPosition	enemyPosition[8] <optimize=false>;
} EnemyPositionGroup;


typedef struct	//12h bytes
{
	ubyte   nothing;
	ubyte   HP_HEAL_10;
	ubyte   HP_HEAL_50;
	ubyte   EP_HEAL_10;
	ubyte   EP_HEAL_50;
	ubyte   CP_HEAL_10;
	ubyte   CP_HEAL_50;
	ubyte	STR_UP_10;
	ubyte	STR_UP_50;
	ubyte	EXP_UP_10;
	ubyte	EXP_UP_50;	
	ubyte   SEPITH_UP;
	ubyte   CRITICAL;
	ubyte   VANISH;
	ubyte   DEATH;
	ubyte   GUARD;
	ubyte   RUSH;
	ubyte	ITEM;
	//ubyte   ARTS_GUARD; // ��ͼƬ����Ч��
	//ubyte   TEAMRUSH;
	//ubyte   unknown;    // û������ͼƬ����Ч��
} ATBonusSet_3rd;

typedef struct	//0Dh bytes
{
	ubyte   nothing;
	ubyte   HP_HEAL_10;
	ubyte   HP_HEAL_50;
	ubyte   EP_HEAL_10;
	ubyte   EP_HEAL_50;
	ubyte   CP_HEAL_10;
	ubyte   CP_HEAL_50;
	ubyte	STR_UP_10;
	ubyte	STR_UP_50;
	ubyte	EXP_UP_10;
	ubyte	EXP_UP_50;	
	ubyte   SEPITH_UP;
	ubyte   CRITICAL;
} ATBonusSet_sc;


typedef struct  //battle\BTTEST00????
{
    ubyte		DUMMY_STRUCT_01[2]  <hidden=true>;
	ushort		Index;
    string		str;
} BATTLExxx;


typedef struct  //һ�� 48�ֽ�
{	
	local int i = 0;
	for (i = 0; i < 8; i++)
		FileIndex	enemyMSFileIndex <format=hex>;
	
	ushort		addrEnemyPosition; 		// ����վλ�����򣬳� ��͵Ϯ ��ʱ.
	ushort		addrEnemyPositionEA;    // ����վλ�����򣬱�͵Ϯ ʱ. EA=EnemyAdvantage
	uint		BGM_Safe;				//�� T_BGMTBL._DT
	uint		BGM_Danger;
	uint		addrATBonus;   			//��ַ��С��һ��ΪA4��bossսΪEC
	
	local uint	addrTemp1 = FTell();
	if (addrEnemyPosition < fileSize)
	{
		FSeek(addrEnemyPosition + addrT_btsetStart);
		EnemyPositionGroup	enemyPosition;
	}
	if (addrEnemyPositionEA < fileSize)
	{	
		FSeek(addrEnemyPositionEA + addrT_btsetStart);
		EnemyPositionGroup	enemyPositionEA;
	}
	if (addrATBonus < fileSize)
	{	
		FSeek(addrATBonus + addrT_btsetStart);
		ATBonusSet_3rd	ATBonus_3rd;
		FSeek(addrATBonus + addrT_btsetStart);
		ATBonusSet_sc	ATBonus_sc;		
	}
	FSeek(addrTemp1);	
} EnemyGroup;

typedef struct
{
	ushort		index  <fgcolor=cGray>;
    ushort		parameter <format=hex,fgcolor=cBlue>;
	//ubyte		DUMMY_STRUCT_02[6];
	//ArrayBytes	temp(6);
	ushort		moveDistance;		// �ƶ���Χ	
	ushort		detectionDistance;	// �з������룬��Զ�����ҷ�
	ubyte		DUMMY_STRUCT_02[4];
	
	local int i = 0;
	for (i = 0; i < 8; i++)
		FileIndex	enemyMSFileIndex <format=hex>;

	ushort		addrEnemyPosition; 		// ����վλ�����򣬳� ��͵Ϯ ��ʱ.
	ushort		addrEnemyPositionEA;    // ����վλ�����򣬱�͵Ϯ ʱ. EA=EnemyAdvantage
	int			addrBattleMap;
	int			BGM;
	int			addrATBonus;
	//ArrayBytes	temp(10);
	/*ushort		moveStyle;			// �ƶ���ʽ��0����ͣͣ��2�����ߡ�3���ƶ����Ҳ���졢5���ƶ������
	//ushort		addrBattleMap;

	if (addrBattleMap < FileSize())
	{
		local uint	addrTemp1 = FTell();
		FSeek(addrBattleMap + addrT_btsetStart + 2);
		ushort	addrBattleMapString;
		FSeek(addrBattleMapString + addrT_btsetStart);		
		string   battleMap;
		FSeek(addrTemp1);  
	}
		
	ubyte		probability1;
	ubyte		probability2;
	ubyte		probability3;
	ubyte		probability4;
	if(probability1 > 0)
		EnemyGroup  enemyGroup1;
	if(probability2 > 0)
		EnemyGroup  enemyGroup2;
	if(probability3 > 0)
		EnemyGroup  enemyGroup3;
	if(probability4 > 0)
		EnemyGroup  enemyGroup4;
	
	local string	display;
	if (probability2 == 0 && probability3 == 0 && probability4 == 0)
	{
		SPrintf(display, "0x%03X: %d", index, probability1);
	}
	else
		SPrintf(display, "0x%03X: %d %d %d %d", index, probability1, probability2, probability3, probability4);
*/
	local string	display;
	SPrintf(display, "0x%03X", index);
} BattleSet <read=readBattleSet,write=writeBattleSet>;

string readBattleSet(BattleSet &a)
{
	return	a.display;
}

void writeBattleSet(BattleSet &a, string s)
{
	// ������
}


typedef struct
{
	local uint		addrT_btsetStart = FTell();
	local uint		fileSize = FileSize();
	local int		i;
	//FSkip(2);
	ushort			addrAddrBattle0;
	local ushort	addrAddrBattleEnd = ReadUShort(addrT_btsetStart + addrAddrBattle0);
	if (addrAddrBattleEnd < addrAddrBattle0)
	{
		addrAddrBattleEnd = addrAddrBattle0 + 2;
	}
	FSeek(addrT_btsetStart + addrAddrBattle0);
	while(1)
	{
		FSkip(2);
		if(ReadUShort(ReadUShort(FTell()) + addrT_btsetStart) == 0xFFFF)
		{
			addrAddrBattleEnd = FTell() - addrT_btsetStart + 2;
			break;
		}
		if(ReadUInt64(FTell()) == 0)
		{
			addrAddrBattleEnd = FTell() - addrT_btsetStart;
			break;
		}
		//if(FTell() >= addrAddrBattleEnd + addrT_btsetStart)
		//	break;
		//if (ReadUShort(FTell()) < addrAddrBattleEnd && addrAddrBattleEnd >= addrAddrBattle0)
		//	addrAddrBattleEnd = ReadUShort(FTell());
	}
	local ushort	TOTAL = (addrAddrBattleEnd - addrAddrBattle0) / 2;

	FSeek(addrT_btsetStart + addrAddrBattle0);
	ushort			addr[TOTAL];//  <hidden=true>;
	
	for (i = 0; i < TOTAL; i++)
	{

		FSeek(addr[i] + addrT_btsetStart);	// ��ת
		BattleSet   battle;
	}	

} T_btset <read=readT_btset>;

string readT_btset(T_btset &a)
{
	return  "ս���趨";
}