package com.busanit501.androidlabtest501.ch18_network

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.busanit501.androidlabtest501.R
import com.busanit501.androidlabtest501.ch18_network.adapter.MyAdapterRetrofit2
import com.busanit501.androidlabtest501.ch18_network.model.newsModel.ItemListModel
import com.busanit501.androidlabtest501.ch18_network.retrofit.MyApplication2
import com.busanit501.androidlabtest501.databinding.ActivityNewsApiTestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsApiTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsApiTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsApiTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 레스트 서버에서(뉴스 API) 데이터 받아오고,

        // 리사이클러뷰 붙이기

        // 변경1
        val networkService = (applicationContext as MyApplication2).networkService

        // 2) 호출하는 함수 콜 만들기. 우리가 만든 인터페이스에 등록된
        // 추상 함수를 이용함. 인자값은 페이지 번호 정의를 문자열 타입으로 했음.

        // 변경2
//        fun getList(
//            //파라미터값 설정
//            //1)q 2) from 3) sortBy 4) apiKey
//            @Query("q") q: String?,
//            @Query("from") from : String,
//            @Query("sortBy") sortBy : String,
//            @Query("apiKey") apiKey : String,
        val QUERY2 = "Apple"
        val from ="2024-06-15"
        val sortBy ="popularity"
        val API_KEY = "b7adb4f936494b3bac62f446ab7686cb"
//        https://newsapi.org/v2/everything?q=Apple&from=2024-06-12&sortBy=popularity&apiKey=b7adb4f936494b3bac62f446ab7686cb
        val userListCall = networkService.getList(QUERY2,from,sortBy,API_KEY)

        //변경3
        // 실제 통신이 시작이 되는 부분, 이 함수를 통해서 데이터를 받아옴.
        userListCall.enqueue(object : Callback<ItemListModel> {
            //익명 클래스가, Callback , 레트로핏2에서 제공하는 인터페이스를 구현했고,
            // 반드시 재정의해야하는 함수들이 있음.
            // 변경4
            override fun onResponse(call: Call<ItemListModel>, response: Response<ItemListModel>) {
                // 데이터를 성공적으로 받았을 때 수행되는 함수
                val userList = response.body()
                // 변경8
                Log.d("lsy","userList의 값 : ${userList?.articles}")

                // 데이터를 성공적으로 받았다면, 여기서 리사이클러 뷰 어댑터에 연결하면 됨.
                // 리사이클러뷰 의 레이아웃 정하는 부분, 기본인 LinearLayoutManager 이용했고,

                //변경6
                val layoutManager = LinearLayoutManager(
                    this@NewsApiTestActivity)
                // 리사이클러뷰에 어댑터 연결
                // 인자값은 : 현재 context : this@HttpTestReqResActivity
                // 2번째 인자값은 : 데이터 , 네트워크 ,레트로핏2 통신으로 받아온 데이터 리스트

                //변경7
                binding.retrofitRecyclerView2.layoutManager = layoutManager
                // 변경9
                binding.retrofitRecyclerView2.adapter =
                    MyAdapterRetrofit2(this@NewsApiTestActivity,userList?.articles)

            }

            //변경5
            override fun onFailure(call: Call<ItemListModel>, t: Throwable) {

                // 데이터를 못 받았을 때 수행되는 함수
                call.cancel()
            }

        })


    }
}