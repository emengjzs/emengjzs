/**
* 2965
*/
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

int is_all_open(int a[]) {
    int i = 0;
    while (i < s_size) {
        if (a[i++] != s_open) {
            return 0;
        }
    }
    return 1;
}


void open(int *a, int k) {
    a[k] = !a[k];
    int i = 0;
    int row = k >> 2;
    int col = k - (row << 2);
    for (i = 0; i < s_board; i++) {
        if (i != col) {
            a[s_board * row + i] = !a[s_board * row + i];
        }
    }
    for (i = 0; i < s_board; i++) {
        if (i != row) {
            a[s_board * i + col] = !a[s_board * i + col];
        }
    }
}

int find_solution(int a[], int len, int k) {
    int *stack = new int[k];
    int top_idx = 0;
    stack[top_idx] = -1;

    while (top_idx >= 0) {
        stack[top_idx]++;
        // the num of elements needed to be selected 
        // less that elements can be selected
        if ((len - (stack[top_idx] + 1) + 1 < k - (top_idx + 1) + 1)) {
            top_idx--;
        }
        else if (top_idx + 1 == k) {
            int i = 0;
            for (i = 0; i < k; i++) {
                open(a, stack[i]);
            }
           
            if (is_all_open(a)) {
                cout << k << endl;
                for (i = 0; i < k; i++) {
                    cout << (stack[i] / 4) + 1 << " " <<  (stack[i] % 4) + 1 << endl;
                }
                delete[] stack;
                return k;
            }
            else {
                for (i = 0; i < k; i++) {
                    open(a, stack[i]);
                }
            }
        }
        else {
            stack[top_idx + 1] = stack[top_idx];
            top_idx++;
        }

    }
    delete[] stack;
    return -1;
}

void find_solution(int a[]) {
    int i = 0;
    int ans = -1;
    for (i = 1; i <= s_size; i++) {
        ans = find_solution(a, s_size, i);
        if (ans != -1) {
            break;
        }
    }
}

int main() {
    int a[s_size] = { 0 };
    create_switches(a);
    find_solution(a);
    return 0;
}