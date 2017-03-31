#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>
#include <iomanip>
#include <locale>
#include <cstring>
using namespace std;

int task1(vector<int>& nums, int k) {
	int max=nums[k];
	for (int i = 0; i < nums.size(); i++) {
		if (max < nums[i]) {
			max = nums[i];
			k = i;
		}
	}	
	return k+1;
}

int main() {
	///////////////////////////////////////////
	vector<int> nums1(6);
	int k = 2, target=5;

	for (int i = 0; i < nums1.size(); i++) {
		cin >> nums1[i];
	}
	cout << task1(nums1, k) << endl;
	cout << endl;
	system("pause");
}