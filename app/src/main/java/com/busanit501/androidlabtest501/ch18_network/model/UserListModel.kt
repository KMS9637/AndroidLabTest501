package com.busanit501.androidlabtest501.ch18_network.model

// data 값의 배열에 요소를 모델링 : UserModel
// UserModel 를 요소로 갖는 리스트 만들기 -> 모델화
data class UserListModel(
    val page: String,
    val perPage: String,
    val total : String,
    val totalPages: String,
    // UserModel 요소로 하는 리스트
    // 이름 : data , 똑같이 해주기.
    // 공공데이터 모델링 할 때, 주의사항 중 하나!!!
    val data: List<UserModel>
)
