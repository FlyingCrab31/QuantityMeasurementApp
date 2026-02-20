#include <stdio.h>

double findMedianSortedArrays(int nums1[], int nums2[], int l1, int l2) {
    int i = 0, j = 0, x = 0;
    int mgArray[l1 + l2];

    while (i < l1 && j < l2) {
        if (nums1[i] <= nums2[j]) {
            mgArray[x++] = nums1[i++];
        } else {
            mgArray[x++] = nums2[j++];
        }
    }

    while (i < l1) {
        mgArray[x++] = nums1[i++];
    }

    while (j < l2) {
        mgArray[x++] = nums2[j++];
    }

    int res = l1 + l2;
    if (res % 2 == 0) {
        return (mgArray[res / 2 - 1] + mgArray[res / 2]) / 2.0;
    } else {
        return mgArray[res / 2];
    }
}

int main() {
    int nums1[] = {1, 2};
    int nums2[] = {3, 4};
    int l1 = sizeof(nums1) / sizeof(nums1[0]);
    int l2 = sizeof(nums2) / sizeof(nums2[0]);

    double median = findMedianSortedArrays(nums1, nums2, l1, l2);

    printf("Median: %lf\n", median);

    return 0;
}
