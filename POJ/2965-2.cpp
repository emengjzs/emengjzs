#include <iostream>

#define s_size 16
#define s_board 4
#define s_close 0
#define s_open 1
using namespace std;


void create_switches(int a[]) {
    int i = 0;
    char ch;
    while (i < s_size) {
        cin >> ch;
        if (ch == '\n')
            continue;
        a[i++] = ch == '+' ? s_close : s_open;
    }
}

void find_solution(int *a, int *b) {
	int i = 0;
	for (i = 0; i < s_size; i ++) {
		if (a[i] == s_close) {
			b[i] ++;
			int i = 0;
		    int row = k >> 2;
		    int col = k - (row << 2);
		    for (i = 0; i < s_board; i++) {
		        if (i != col) {
		            b[s_board * row + i]++;
		        }
		    }
		    for (i = 0; i < s_board; i++) {
		        if (i != row) {
		            b[s_board * i + col] ++;
		        }
		    }
		}
	}

	int n = 0;
	for (i = 0; i < s_size; i ++) {
		if ((b[i] >> 1) & 0x01) {
			n ++;
		}
	}
	for (i = 0; i < s_size; i ++) {
		if ((b[i] >> 1) & 0x01) {
			cout << (i / 4) + 1 << " " <<  (i % 4) + 1 << endl;

		}
	}
	

}



int main() {
    int a[s_size] = { 0 };
    int count[s_size] = {0};
    create_switches(a);
    find_solution(a, count);
    return 0;
}