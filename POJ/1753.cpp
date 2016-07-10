/**
 * POJ 1753
 * 
 */
#include <iostream>
#define p_size 16

using namespace std;

int isFull(int *a) {
    int a0 = a[0];
    int i = 0;
    for(i = 1; i < 16; i ++) {
        if (a[i] != a0) {
            return 0;
        }
    }
    return 1;
}

void change_color(int *a, int i) {
    a[i] = !a[i];
    int row =  i / 4;
    int col = i % 4;
    if ( row > 0) {
        a[i - 4] = !a[i - 4];
    }
    if (row < 3) {
        a[i + 4] = !a[i + 4];
    }
    if (col > 0) {
        a[i - 1] = !a[i - 1];
    }
    if (col < 3) {
        a[i + 1] = !a[i + 1];
    }

}


int search(int *a, int length, int k) {


    int *stack = new int[k];

    int top_idx = 0;
    stack[top_idx] = -1;

    while(top_idx >= 0) {
        stack[top_idx] ++;

        if ( length - stack[top_idx] < k - top_idx) {
            top_idx --;
        }
        else if (top_idx == k - 1) {

            int j = 0;
            for (j = 0; j < k; j++) {
                change_color(a, stack[j]);
            }
            if (isFull(a)) {
                delete[] stack;
                return k;
            }
            else {
                for (j = 0; j < k; j++) {
                    change_color(a, stack[j]);
                }
                continue;
            }
        }

        
        else {
            stack[top_idx + 1] = stack[top_idx];
            top_idx ++;
            continue; 
        }
    }
    delete[] stack;
    return -1;
}

int main() {
    char ch;
    int a[16] = {};

    int i = 0;
    while (i < 16) {
        cin >> ch;
        if (ch == '\n')
            continue;
        else if (ch == 'w') 
            a[i ++] = 0;
        else if (ch == 'b')
            a[i ++] = 1;
    }

    if (isFull(a)) {
        cout<< 0 << endl;
    }
    else {
        for (i = 1; i <= 16; i ++) {
            int ans = search(a, 16, i);
            if (ans > 0) {
                cout << ans << endl;
                break;
            }
        }
        if (i > 16) {
            cout << "Impossible" << endl;
        }
    }
    return 0;
}