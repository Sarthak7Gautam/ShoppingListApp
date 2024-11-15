package gautam.sarthak.shoppinglist.ui.theme

import android.content.ClipData.Item
import android.os.Parcel
import android.os.Parcelable
import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.room.util.copy

data class ShoppingData(val name:String,val quantity:Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen() {

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableIntStateOf(0) }
    var showdialog by remember { mutableStateOf(false) }
    var items by remember { mutableStateOf(listOf<ShoppingData>()) }
    var editIndex by remember{ mutableIntStateOf(-1) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            name = ""
            quantity = 0
            editIndex = -1
            showdialog = true }) {
            Text(text = "Add Item")
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            itemsIndexed(items) { index, item->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = item.name)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Quantity:${item.quantity}")
                }
                Row {
                    IconButton(onClick = {
                        name = item.name
                        quantity = item.quantity
                        editIndex = index
                        showdialog = true
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        items = items.toMutableList().apply { removeAt(index) }
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
        if (showdialog) {
            AlertDialog(onDismissRequest = { showdialog = false },
                title = {
                    Text(text = if(editIndex>=0) "Edit the ListItem" else "Add the ListItem")
                },
                text = {
                    Column{
                        OutlinedTextField(value = name, onValueChange = { name = it })
                        Spacer(modifier = Modifier.padding(12.dp))
                        OutlinedTextField(
                            value = if(quantity==0)"" else quantity.toString(),
                            onValueChange = { quantity = it.toIntOrNull() ?: 1 })
                        }},
                confirmButton = { TextButton(onClick = {
                    if(name.isNotBlank()){
                        if(editIndex>=0) {
                            items=items.toMutableList().apply {
                                this[editIndex]= ShoppingData(name,quantity)
                            }
                        }
                    else{
                        items=items+ShoppingData(name,quantity)
                    }
                        showdialog = false}
                }) {
                    Text(text = "Add ")
                } },
                dismissButton = { TextButton(onClick = { showdialog=false}) {
                    Text(text = "Cancel")
                }})
        }
    }
}


