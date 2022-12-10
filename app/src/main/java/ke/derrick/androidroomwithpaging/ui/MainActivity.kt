package ke.derrick.androidroomwithpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ke.derrick.androidroomwithpaging.viewmodels.MainActivityViewModel
import ke.derrick.androidroomwithpaging.viewmodels.MainActivityViewModelFactory
import ke.derrick.androidroomwithpaging.RandomTextApplication
import ke.derrick.androidroomwithpaging.databinding.ActivityMainBinding
import ke.derrick.androidroomwithpaging.ui.adapters.RandomTextListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            (application as RandomTextApplication).database.randomTextDao())}
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initAddRandomTextListener()
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun initRecyclerView() {
        val randomTextAdapter = RandomTextListAdapter(this)
        recyclerView = binding.recyclerView

        lifecycleScope.launch {
            viewModel.allRandomTexts.collectLatest {
                randomTextAdapter.submitData(it)
            }

        }
        recyclerView.adapter = randomTextAdapter
    }

    private fun initAddRandomTextListener() {
        binding.btnAdd.setOnClickListener {
            addRandomText()
        }
        binding.inputText.setOnEditorActionListener {
                _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addRandomText()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.inputText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addRandomText()
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun addRandomText() {
        val randomText = binding.inputText.text.toString()
        viewModel.addRandomText(randomText)
        Toast.makeText(this, "$randomText added successfully", Toast.LENGTH_LONG).show()
    }

    private val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val randomText = (viewHolder as RandomTextListAdapter.RandomTextViewHolder).randomText
            randomText?.let { viewModel.deleteRandomText(randomText) }
        }

    })

}