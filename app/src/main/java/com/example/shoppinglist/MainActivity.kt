package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListApp()
        }
    }
}

@Composable
fun ShoppingListApp() {
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var shoppingList by remember { mutableStateOf(listOf<ShoppingItem>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Shopping List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                shoppingList = shoppingList + ShoppingItem(itemName, itemQuantity)
                itemName = ""
                itemQuantity = ""
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(shoppingList) { shoppingItem ->
                ShoppingListItem(shoppingItem, onCheckedChange = { checked ->
                    shoppingList = shoppingList.map {
                        if (it == shoppingItem) it.copy(isBought = checked) else it
                    }
                })
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${item.name} - ${item.quantity}",
            textDecoration = if (item.isBought) TextDecoration.LineThrough else TextDecoration.None
        )
        Checkbox(checked = item.isBought, onCheckedChange = onCheckedChange)
    }
}

data class ShoppingItem(val name: String, val quantity: String, val isBought: Boolean = false)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShoppingListApp()
}
