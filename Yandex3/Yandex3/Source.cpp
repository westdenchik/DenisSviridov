#include <iostream>
#include <locale>
#include <vector>
#include <string>
using namespace std;
string task3(string s)
{
	for (int i = 0; i < s.size(); i++) {
		for (int k = i+1; k < s.size(); k++) {
			if (s.at(k) == s.at(i)) {			
				s.erase(k, 1);
				k--;
			}
		}
	}		
	return s;
}
int main() {
	string ptr, ptr1;
	getline(cin, ptr);
	cout << task3(ptr) << endl;
	system("pause");
	return 0;
}