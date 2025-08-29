package com.cret.recyclerviewpractice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cret.recyclerviewpractice.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    private lateinit var adapterOfAList: MultiTypeItemAdapter
    private lateinit var adapterOfBList: MultiTypeItemAdapter

    // 화면 전환 시에도 리스트 아이템들을 유지하기 위해 리스트를 전역 변수로 선언
    private val listA = mutableListOf<UiItem>()
    private val listB = mutableListOf<UiItem>()
    
    private var itemCounter = 0
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            itemCounter = savedInstanceState.getInt("itemCounter")
            listA.addAll(BundleCompat.getParcelableArrayList<UiItem>(savedInstanceState, "listA", UiItem::class.java) ?: emptyList())
            listB.addAll(BundleCompat.getParcelableArrayList<UiItem>(savedInstanceState, "listB", UiItem::class.java) ?: emptyList())
        }
        
        setupRecyclerViews()
        setupButtons()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("itemCounter", itemCounter)
        outState.putParcelableArrayList("listA", ArrayList(listA))
        outState.putParcelableArrayList("listB", ArrayList(listB))
    }
    
    private fun setupRecyclerViews() {
        // A 리스트 설정
        adapterOfAList = MultiTypeItemAdapter(listA) { item ->
            // A 리스트 아이템 클릭 시 B 리스트로 이동
            moveItemToBList(item)
        }
        binding.recyclerViewA.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterOfAList
            addItemDecoration(CustomDividerItemDecoration(this@MainActivity))
        }
        
        // B 리스트 설정
        adapterOfBList = MultiTypeItemAdapter(listB)
        binding.recyclerViewB.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterOfBList
            addItemDecoration(CustomDividerItemDecoration(this@MainActivity))
        }
    }
    
    private fun setupButtons() {
        binding.btnAddItem.setOnClickListener {
            addRandomItemToAList()
        }
        
        binding.btnRemoveItem.setOnClickListener {
            removeFirstItemFromBList()
        }
    }
    
    private fun addRandomItemToAList() {
        val type = Random.nextInt(3)
        val newItem = when (type) {
            0 -> TextItem(itemCounter, getString(R.string.only_text))
            1 -> ImageItem(itemCounter, R.drawable.baseline_account_circle_24)
            else -> TextImageItem(itemCounter, getString(R.string.with_text), R.drawable.baseline_account_circle_24)
        }
        adapterOfAList.addItem(newItem)
        itemCounter++
        showToast("A 리스트에 ${newItem.id}번 아이템이 추가되었습니다")
    }
    
    private fun moveItemToBList(item: UiItem) {

        //A 리스트에서 아이템 제거
        adapterOfAList.removeItem(item)

        // B 리스트에 아이템 추가
        adapterOfBList.addItem(item)
        
        showToast("${item.id}번 아이템이 B 리스트로 이동되었습니다")
    }
    
    private fun removeFirstItemFromBList() {
        val removedItem = adapterOfBList.removeFirstItem()
        if (removedItem != null) {
            showToast("B 리스트에서 ${removedItem.id}번 아이템이 삭제되었습니다")
        } else {
            showToast("B 리스트가 비어있습니다")
        }
    }
    
    private fun showToast(message: String) {
        // 기존 토스트가 있다면 취소
        toast?.cancel()
        
        // 새로운 토스트 생성 및 표시
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}