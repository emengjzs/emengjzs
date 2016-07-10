

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