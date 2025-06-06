package com.example.ecommerceapp.view

import ApiClient
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentDashboardBinding
import com.example.ecommerceapp.models.Category
import com.example.ecommerceapp.models.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private val categoryList = mutableListOf<Category>()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? LoginActivity)?.findViewById<ImageView>(R.id.ivLogo)?.visibility = View.GONE
        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)


        binding.recyclerCategories.layoutManager = GridLayoutManager(requireContext(), 2)

        categoryAdapter = CategoryAdapter(categoryList) { category ->
            val fragment = ProductFragment()
            val bundle = Bundle().apply {
                putString("subCategoryId", category.categoryId)
            }
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerCategories.adapter = categoryAdapter

        getCategories()

        drawerToggle = ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val headerView = binding.navigationView.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvEmail = headerView.findViewById<TextView>(R.id.tvUserEmail)
        val tvPhone = headerView.findViewById<TextView>(R.id.tvUserMobile)

        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        tvUserName.text = "Welcome " + (sharedPref.getString("name", "User") ?: "")
        tvEmail.text = sharedPref.getString("email", "")
        tvPhone.text = sharedPref.getString("phone", "")

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(requireContext(), "Home clicked", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_cart -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, CartFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_orders -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, OrderFragment())
                    .addToBackStack(null)
                    .commit()
            }

                R.id.nav_logout -> {
                    val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    sharedPref.edit().clear().apply()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, LoginFragment())
                        .commit()
                }
            }

            binding.drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(requireContext(), "Search clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getCategories() {
        val call = ApiClient.getApiService().getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    android.util.Log.d("CATEGORY_DEBUG", "Response Body: $body")

                    if (body?.status == 0) {
                        val categories = body.categories
                        android.util.Log.d("CATEGORY_DEBUG", "Categories: $categories")

                        categoryList.clear()
                        categoryList.addAll(categories)
                        categoryAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Status not 0: ${body?.status}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "HTTP Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    android.util.Log.e("CATEGORY_DEBUG", "Error Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                android.util.Log.e("CATEGORY_DEBUG", "Failure: ${t.message}", t)
            }
        })
    }

}
