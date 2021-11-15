package com.example.cinebuzz.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.home.TrendingPage
import com.example.cinebuzz.recyclerview.HomePageAdapter
import com.example.cinebuzz.retrofit.Latest
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout


class HomePage_fragment :Fragment(){

   private var movies1= mutableListOf<MoviesDataItem>()
   private var movies2= mutableListOf<MoviesDataItem>()
   private var movies3= mutableListOf<MoviesDataItem>()
   private var movies4= mutableListOf<MoviesDataItem>()
   private var movies5= mutableListOf<MoviesDataItem>()
private lateinit var Shimmer1:ShimmerFrameLayout
private lateinit var Shimmer2:ShimmerFrameLayout
private lateinit var Shimmer3:ShimmerFrameLayout
private lateinit var Shimmer4:ShimmerFrameLayout
private lateinit var Shimmer5:ShimmerFrameLayout
private lateinit var Shimmer6:ShimmerFrameLayout
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView
    private lateinit var recyclerView5: RecyclerView

    private lateinit var adapter1:HomePageAdapter
    private lateinit var adapter2:HomePageAdapter
    private lateinit var adapter3:HomePageAdapter
    private lateinit var adapter4:HomePageAdapter
    private lateinit var adapter5:HomePageAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val context = context
        val view = inflater.inflate(R.layout.home_page, container, false)
        Shimmer1=view.findViewById(R.id.homeShimmer1)
        Shimmer2=view.findViewById(R.id.homeShimmer2)
        Shimmer3=view.findViewById(R.id.homeShimmer3)
        Shimmer4=view.findViewById(R.id.homeShimmer4)
        Shimmer5=view.findViewById(R.id.homeShimmer5)
        Shimmer6=view.findViewById(R.id.Slidershimmer)
        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()
        var picture=ArrayList<Latest>()
        val request = ServiceBuilder.buildService()
        val call = request.latest()
        call.enqueue(object : Callback<List<Latest>?> {
            override fun onResponse(call: Call<List<Latest>?>, response: Response<List<Latest>?>) {
                if(response.isSuccessful) {

                    Shimmer6.stopShimmer()
                    Shimmer6.visibility=View.GONE
                    val responseBody=response.body()!!
                    for(movie in responseBody) {
                        imageList.add(SlideModel(movie.posterurl,ScaleTypes.FIT))
                        picture.add(movie)
                    }
                    imageSlider.setImageList(imageList)

                    imageSlider.setItemClickListener(object : ItemClickListener {

                        override fun onItemSelected(position: Int) {
                            val intent=Intent(context,PlayMovie::class.java)
//                            intent.putExtra("MOVIEID", response._id)
//                                       startActivity(intent)

                                when(position) {
                                   0 -> {
                                       intent.putExtra("MOVIEID", picture[0]._id)
                                       startActivity(intent)
                                   }
                                    1 -> {
                                        intent.putExtra("MOVIEID", picture[1]._id)
                                        startActivity(intent)
                                    }
                                    2 -> {
                                        intent.putExtra("MOVIEID", picture[2]._id)
                                        startActivity(intent)
                                    }
                                    3 -> {
                                        intent.putExtra("MOVIEID", picture[3]._id)
                                        startActivity(intent)
                                    }
                                    4 -> {
                                        intent.putExtra("MOVIEID", picture[4]._id)
                                        startActivity(intent)
                                    }


                            }

                        }
                    })
                }

            }

            override fun onFailure(call: Call<List<Latest>?>, t: Throwable) {
                Toast.makeText(context,"${t.message}",Toast.LENGTH_SHORT).show()
            }
        })


        recyclerView1=view.findViewById(R.id.Trending)
        recyclerView2=view.findViewById(R.id.Action)
        recyclerView3=view.findViewById(R.id.Horror)
        recyclerView4=view.findViewById(R.id.Comedy)
        recyclerView5=view.findViewById(R.id.Drama)
//        recyclerView1.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView2.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView3.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView4.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView5.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView1.adapter = HomePageAdapter(context,movies)
//        recyclerView2.adapter=HomePageAdapter(context,movies)
//        recyclerView3.adapter=HomePageAdapter(context,movies)
//        recyclerView4.adapter=HomePageAdapter(context,movies)
//        recyclerView5.adapter=HomePageAdapter(context,movies)


       val request1 = ServiceBuilder.buildService()
       val call1 = request1.trending()
       call1.enqueue(object : Callback<List<MoviesDataItem>?> {
           override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
               if(response.isSuccessful) {

                   Shimmer1.stopShimmer()
                   Shimmer1.visibility=View.GONE
                   val responseBody = response.body()!!
                   for(movie in responseBody) {
                       movies1.add(movie)
                   }

                   adapter1= HomePageAdapter(activity,movies1)
                   recyclerView1.adapter=adapter1
                   recyclerView1.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

                   adapter1.setOnItemClickListener(object :HomePageAdapter.onItemClickListener{
                       override fun onItemClick(position: Int) {

                           //Toast.makeText(context,"you clicked$position",Toast.LENGTH_SHORT).show()

                           val intent=Intent(context,PlayMovie::class.java)
                           intent.putExtra("MOVIEID",adapter1.movies[position]._id)
                           startActivity(intent)
                       }
                   })
               }

           }

           override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
               Toast.makeText(context,"failed ${t.message}",Toast.LENGTH_SHORT).show()
           }
       })
        val request2 = ServiceBuilder.buildService()
        val call2 = request2.action()
        call2.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {

                    Shimmer2.stopShimmer()
                    Shimmer2.visibility=View.GONE
                    val responseBody = response.body()!!
                    for(movie in responseBody) {
                        movies2.add(movie)
                    }
                    adapter2= HomePageAdapter(activity,movies2)
                    recyclerView2.adapter=adapter2
                    recyclerView2.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

                    adapter2.setOnItemClickListener(object :HomePageAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            //Toast.makeText(context,"you clicked$position",Toast.LENGTH_SHORT).show()

                           val intent=Intent(context,PlayMovie::class.java)
                            intent.putExtra("MOVIEID",adapter2.movies[position]._id)
                           startActivity(intent)
                        }
                    })
                }

            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                Toast.makeText(context,"failed ${t.message}",Toast.LENGTH_SHORT).show()


            }
        })
        val request3 = ServiceBuilder.buildService()
        val call3= request3.comedy()
        call3.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {

                    Shimmer3.stopShimmer()
                    Shimmer3.visibility=View.GONE
                    val responseBody = response.body()!!
                    for(movie in responseBody) {
                        movies3.add(movie)
                    }
                    adapter3= HomePageAdapter(activity,movies3)
                    recyclerView4.adapter=adapter3
                    recyclerView4.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

                    adapter3.setOnItemClickListener(object :HomePageAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            //Toast.makeText(context,"you clicked$position",Toast.LENGTH_SHORT).show()

                           val intent=Intent(context,PlayMovie::class.java)
                            intent.putExtra("MOVIEID",adapter3.movies[position]._id)
                           startActivity(intent)
                        }
                    })
                }

            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                Toast.makeText(context,"failed ${t.message}",Toast.LENGTH_SHORT).show()
            }
        })
        val request4 = ServiceBuilder.buildService()
        val call4 = request4.horror()
        call4.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {

                    Shimmer4.stopShimmer()
                    Shimmer4.visibility=View.GONE
                    val responseBody = response.body()!!
                    for(movie in responseBody) {
                        movies4.add(movie)
                    }
                    adapter4= HomePageAdapter(activity,movies4)
                    recyclerView3.adapter=adapter4
                    recyclerView3.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

                    adapter4.setOnItemClickListener(object :HomePageAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            //Toast.makeText(context,"you clicked$position",Toast.LENGTH_SHORT).show()

                           val intent=Intent(context,PlayMovie::class.java)
                            intent.putExtra("MOVIEID",adapter4.movies[position]._id)
                           startActivity(intent)
                        }
                    })
                }

            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                Toast.makeText(context,"failed ${t.message}",Toast.LENGTH_SHORT).show()
            }
        })
        val request5 = ServiceBuilder.buildService()
        val call5 = request5.drama()
        call5.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {

                    Shimmer5.stopShimmer()
                    Shimmer5.visibility=View.GONE
                    val responseBody = response.body()!!
                    for(movie in responseBody) {
                        movies5.add(movie)
                    }
                    adapter5= HomePageAdapter(activity,movies5)
                    recyclerView5.adapter=adapter5
                    recyclerView5.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

                    adapter5.setOnItemClickListener(object :HomePageAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            //Toast.makeText(context,"you clicked$position",Toast.LENGTH_SHORT).show()

                           val intent=Intent(context,PlayMovie::class.java)
                            intent.putExtra("MOVIEID",adapter5.movies[position]._id)
                           startActivity(intent)
                        }
                    })
                }

            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                Toast.makeText(context,"failed ${t.message}",Toast.LENGTH_SHORT).show()
            }
        })



//        imageList.add(SlideModel("https://images.immediate.co.uk/production/volatile/sites/3/2019/04/Avengers-Endgame-Banner-2-de7cf60.jpg?quality=90&resize=620,413",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://live.staticflickr.com/1980/29996141587_7886795726_b.jpg",ScaleTypes.FIT))
//        imageSlider.setImageList(imageList)

        val Arrow1=view.findViewById<ImageView>(R.id.Arrow1)
        val Arrow2=view.findViewById<ImageView>(R.id.Arrow2)
        val Arrow3=view.findViewById<ImageView>(R.id.Arrow3)
        val Arrow4=view.findViewById<ImageView>(R.id.Arrow4)
        val Arrow5=view.findViewById<ImageView>(R.id.Arrow5)

        Arrow1.setOnClickListener {
            val intent = Intent(activity,TrendingPage::class.java)
            intent.putExtra("Category","trending")
            startActivity(intent)
        }

        Arrow2.setOnClickListener {
            val intent = Intent(activity,TrendingPage::class.java)
            intent.putExtra("Category","action")
            startActivity(intent)
        }
        Arrow3.setOnClickListener {
            val intent = Intent(activity,TrendingPage::class.java)
            intent.putExtra("Category","comedy")
            startActivity(intent)
        }
        Arrow4.setOnClickListener {
            val intent = Intent(activity,TrendingPage::class.java)
            intent.putExtra("Category","horror")
            startActivity(intent)
        }
        Arrow5.setOnClickListener {
            val intent = Intent(activity,TrendingPage::class.java)
            intent.putExtra("Category","drama")
            startActivity(intent)
        }
        return view
    }
}