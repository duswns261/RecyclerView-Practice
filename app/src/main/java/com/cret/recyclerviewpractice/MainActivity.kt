package com.cret.recyclerviewpractice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cret.recyclerviewpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    private lateinit var adapterOfAList: ItemAdapter
    private lateinit var adapterOfBList: ItemAdapter
    
    private var itemCounter = 0
    private var currentToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerViews()
        setupButtons()
    }
    
    private fun setupRecyclerViews() {
        // A 리스트 설정
        adapterOfAList = ItemAdapter(mutableListOf()) { item ->
            // A 리스트 아이템 클릭 시 B 리스트로 이동
            moveItemToB(item)
        }
        binding.recyclerViewA.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterOfAList
            addItemDecoration(CustomDividerItemDecoration(this@MainActivity))
        }
        
        // B 리스트 설정
        adapterOfBList = ItemAdapter(mutableListOf())
        binding.recyclerViewB.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterOfBList
            addItemDecoration(CustomDividerItemDecoration(this@MainActivity))
        }
    }
    
    private fun setupButtons() {
        binding.btnAddItem.setOnClickListener {
            addNewItemToA()
        }
        
        binding.btnRemoveItem.setOnClickListener {
            removeFirstItemFromB()
        }
    }
    
    private fun addNewItemToA() {
        val newItem = Item(itemCounter, "아이템 $itemCounter")
        adapterOfAList.addItem(newItem)
        itemCounter++
        showToast("A 리스트에 ${newItem.id}번 아이템이 추가되었습니다")
    }
    
    private fun moveItemToB(item: Item) {
        // A 리스트에서 아이템 제거
        val itemsA = adapterOfAList.getItems().toMutableList()
        itemsA.remove(item)
        
        // A 어댑터 새로고침
        adapterOfAList = ItemAdapter(itemsA) { clickedItem ->
            moveItemToB(clickedItem)
        }
        binding.recyclerViewA.adapter = adapterOfAList
        
        // B 리스트에 아이템 추가
        adapterOfBList.addItem(item)
        
        showToast("${item.id}번 아이템이 B 리스트로 이동되었습니다")
    }
    
    private fun removeFirstItemFromB() {
        val removedItem = adapterOfBList.removeFirstItem()
        if (removedItem != null) {
            showToast("B 리스트에서 ${removedItem.id}번 아이템이 삭제되었습니다")
        } else {
            showToast("B 리스트가 비어있습니다")
        }
    }
    
    private fun showToast(message: String) {
        // 기존 토스트가 있다면 취소
        currentToast?.cancel()
        
        // 새로운 토스트 생성 및 표시
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }
}