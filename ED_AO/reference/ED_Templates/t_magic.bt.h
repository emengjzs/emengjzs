//--------------------------------------
//--- 010 Editor v3.2 Binary Template
//
// File: ��֮�켣/��֮�켣/��֮�켣 t_magic._dt Template
// Author:acgngca
// Revision:2011-11-18
// Purpose:�鿴���޸�ħ��ս��Ч��
// Readme:�κθı��ļ���С�Ĳ����������Խ��У��������Ѿ��������ļ��ĸ�ʽ
//
//--------------------------------------

// ħ��ս������ ����
//-----------------------------------------------------------------------------------------------------------
typedef struct(int parMAGIC)
{
	if (parMAGIC == 1)
	{
		ushort	AnimationType;
		ubyte	Target; 
		ubyte	SpecialEffect;		// 01 ǿ������ 02 ����ʱ�޷������
		ubyte	Att;				// ����
		// �չ�FCSC		0�� 1�� 2ˮ 3�� 4�� 5�� 6�� 0ʱ	// FCSCû�� ʱ�ջ���Ч�ʣ���������ν
		// �չ�3rd		0�� 1�� 2ˮ 3�� 4�� 5�� 6�� 7ʱ
		// ���̹�		0�� 1�� 2ˮ 3�� 4�� 5ʱ 6�� 7��
		ubyte	ShapeScope;			// ��Χ��״
		ubyte	Effect1;
		ubyte	Effect2;
		if (MAGICSIZE == 32)
		{
			ushort	RNG;
			ushort	ScopeRadius;		// �뾶
			ushort	STCharge;
			ushort	STCoolDown;
			ushort	CP_EP;
			ushort	DisplayIndex;		// ħ���б��е���ʾ�Ⱥ�t_magic._dt�еĲ���Ч
		}
		else if (MAGICSIZE == 28)
		{
			ubyte	DisplayIndex;		// ħ���б��е���ʾ�Ⱥ�t_magic._dt�еĲ���Ч
			ubyte	RNG;
			ubyte	STCharge;
			ubyte	STCoolDown;
			ushort	CP_EP;
			ushort	ScopeRadius;		// �뾶
		}
		short	Effect1Parameter;
		ushort	Effect1ST;
		short	Effect2Parameter;
		ushort	Effect2ST;

		local uint  addrTemp = FTell();

		ushort  addrMagicName <hidden=true>;	// ħ���� ��ַ
		ushort  addrMagicIntro <hidden=true>;	// ħ��˵�� ��ַ
		local uint addrMagicNameReal = addrMagicName + addrT_magic;
		local uint addrMagicIntroReal = addrMagicIntro + addrT_magic;
		if (addrMagicName != 0)
		{
			FSeek(addrMagicNameReal);		// ��ת
			string  MagicName;
			FSeek(addrMagicIntroReal);		// ��ת
			string	MagicIntro;
		}
		else
		{
			local string	MagicName = "nul \t";
		}
/* �ڴ���
		// ħ������ֵ
		if (i >=10 && i <= 134)	// ��׼ȷ�����С� ħ���б��������ֱ��ȥ��t_magqrt, ֻҪMagicIndex�Ե���������ֵ���ͻ����, �����ǲ���Art��û�ж���. ������ΧΪ(����)max(134?, ReadUShort(0)/2).
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
		ubyte 			DUMMY_STRUCT_01;	// ȥ�� WARNING Line xx: Empty structure.
		local string	MagicName = "nul \t";
	}
} MAGIC <read=readMagicName,write=writeMagicName>;

string readMagicName(MAGIC &a)
{
	return	a.MagicName;
}

void writeMagicName(MAGIC &a, string s)
{
	// ������
}

typedef struct
{
	// ħ������
	if (ReadUShort(addrT_magic) == 0x72F0)
	{
		local ushort	TOTAL = 373;	// ���ϲ�������
	}
	else if (ReadUShort(addrT_magic) < ReadUShort(addrT_magic + 2))
	{
		local ushort	TOTAL = ReadUShort(addrT_magic)/2;	// ���350 �̹�433
	}

	// ħ�������� �̹�24+4 �չ����28+4
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
	ushort			addr[TOTAL]  <hidden=true>;	// ħ���� ��ַ

	for (i = 0; i < TOTAL-1; i++)
	{
		if (addr[i+1] - addr[i] >= MAGICSIZE)
		{
			FSeek(addr[i] + addrT_magic);	// ��ת
			MAGIC	magic(1);
		}
		else
		{
			FSeek(addrT_magic);
			MAGIC	magic(0) <hidden=true>;	// ������Чħ��
		}
	}

	// ������һ��
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
	return  "ħ��ս������";
}
