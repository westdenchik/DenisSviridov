//Task 2 , �������� ����� ���������, ��� ���������� � ������
#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>
#include <iomanip>
#include <locale>
#include <cstring>
using namespace std;


bool task2(vector<vector<int>>& matrix, int target) {
	int t = target;
	bool tr = false;
	for (size_t i = 0; i < 5; ++i)
	{
		matrix[i].resize(5);
		for (size_t j = 0; j < 5; ++j)
			if (matrix[i][j] == t) {
				tr = true;
				break;
			}
		cout << endl;
	}
	return tr;
	}

int main(){
	///////////////////////////////////////////
	setlocale(LC_ALL, "");
	int k = 2, target, temp;
	cout << "\n������� ����� ������� ���������� �����\n";
	cin >> target;
	cout << "������� ������� �����\n";
	vector<vector <int>> Matrix(5);
	for (size_t i = 0; i < 5; ++i)
	{
		Matrix[i].resize(5);
		for (size_t j = 0; j < 5; ++j)
			cin >> Matrix[i][j];
	}
	////////////////////////////////////////////
	for (size_t i = 0; i < 5; i++)
	{
		for (size_t j = i; j < 5; j++)
			if (Matrix[i][j] < Matrix[j][i]) {
				temp = Matrix[i][j];
				Matrix[i][j] = Matrix[j][i];
				Matrix[j][i] = temp;
			}
	}
	////////////////////////////////////////////
	cout << "��������� �������\n";
	for_each(Matrix.begin(), Matrix.end(), [](vector<int>& ivec)
	{
		for_each(ivec.begin(), ivec.end(), [](int i)
		{
			cout << left << setw(5) << i;
		});
		cout << endl;
	});
	/////////////////////////////////////////////
	cout << endl;
	cout << "\n������� ����� " << target << ":";
	if (task2(Matrix, target) == true) {
		cout << "�������";
	}
	else {
		cout << "�� �������";
	}
	cout << endl;
	///////////////////////////////////////////
	system("pause");
}