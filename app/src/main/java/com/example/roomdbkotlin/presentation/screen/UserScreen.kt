package com.example.roomdbkotlin.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomdbkotlin.data.utils.Resource
import com.example.roomdbkotlin.presentation.viewmodal.UserViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel()
) {

    var name by rememberSaveable {
        mutableStateOf("")
    }

    var phNo by rememberSaveable {
        mutableStateOf("")
    }


    var editUserId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

   // val users by viewModel.users.collectAsState()
    val state by viewModel.usersState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("Enter Name")
                }
            )
            OutlinedTextField(
                value = phNo,
                onValueChange = {

                    // ONLY NUMBERS + MAX 10 DIGITS
                    if (
                        it.all { char -> char.isDigit() } &&
                        it.length <= 10
                    ) {

                        phNo = it
                    }
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("Enter Phone")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )


        }



        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                if (name.isBlank()) return@Button

                // CHECK DUPLICATE NAME
                val users =
                    (state as? Resource.Success)?.data
                        ?: emptyList()

                val alreadyExists = users.any {

                    it.name.equals(name, ignoreCase = true) &&
                            it.id != editUserId
                }

                if (alreadyExists) {
                    return@Button
                }

                if (editUserId == null) {

                    // INSERT
                    viewModel.insert(name, phNo)

                } else {

                    // UPDATE
                    viewModel.update(
                        id = editUserId!!,
                        name = name,
                        phonenumber = phNo
                    )

                    editUserId = null
                }

                name = ""
                phNo = ""
            }
        ) {

            Text(
                if (editUserId == null)
                    "Add User"
                else
                    "Update User"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (state) {

            is Resource.Loading -> {

                Text("Loading...")
            }

            is Resource.Error -> {

                Text(
                    text = state.message ?: "Error"
                )
            }

            is Resource.Success -> {

                val users = state.data ?: emptyList()

                LazyColumn {

                    items(users) { user ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                "${user.name} - ${user.phonenumber}"
                            )

                            Row {

                                Button(
                                    onClick = {

                                        name = user.name

                                        phNo =
                                            user.phonenumber
                                                .toString()

                                        editUserId = user.id
                                    }
                                ) {

                                    Text("Edit")
                                }

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Button(
                                    onClick = {
                                        viewModel.delete(user)
                                    }
                                ) {

                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}