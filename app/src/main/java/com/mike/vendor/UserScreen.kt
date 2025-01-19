package com.mike.vendor

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.vendor.model.users.User
import com.mike.vendor.model.users.UserType
import com.mike.vendor.model.users.UserViewmodel
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {
    val viewModel: UserViewmodel = hiltViewModel()
    val users by viewModel.users.collectAsState()
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf(UserType.CUSTOMER) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var showAddUserForm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllUsers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Management") },
                actions = {
                    IconButton(onClick = { showAddUserForm = !showAddUserForm }) {
                        Icon(
                            imageVector = if (showAddUserForm) Icons.Filled.Close else Icons.Filled.Add,
                            contentDescription = if (showAddUserForm) "Hide Form" else "Show Form"
                        )
                    }
                    IconButton(onClick = { viewModel.getAllUsers() }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            AnimatedVisibility(
                visible = showAddUserForm,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Add New User",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = { Text("First Name") },
                            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Last Name") },
                            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Phone Number") },
                            leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            singleLine = true
                        )

                        ExposedDropdownMenuBox(
                            expanded = isDropdownExpanded,
                            onExpandedChange = { isDropdownExpanded = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            OutlinedTextField(
                                value = userType.name.lowercase().capitalize(),
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("User Type") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Person,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                UserType.values().forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type.name.lowercase().capitalize()) },
                                        onClick = {
                                            userType = type
                                            isDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = {
                                if (firstName.isNotEmpty() && lastName.isNotEmpty() &&
                                    email.isNotEmpty() && phoneNumber.isNotEmpty()
                                ) {
                                    val newUser = User(
                                        id = UUID.randomUUID().toString(),
                                        firstName = firstName,
                                        lastName = lastName,
                                        email = email,
                                        phoneNumber = phoneNumber,
                                        userType = userType
                                    )
                                    viewModel.insertUser(
                                        newUser,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "User added successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            firstName = ""
                                            lastName = ""
                                            email = ""
                                            phoneNumber = ""
                                            showAddUserForm = false
                                        },
                                        onError = { error ->
                                            Toast.makeText(
                                                context,
                                                "Error: $error",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please fill all fields",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add User")
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (users.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PeopleOutline,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No users found",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(users.size) { index ->
                        UserItem(user = users[index])
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${
                        user.firstName.firstOrNull()?.uppercase()
                    }${user.lastName.firstOrNull()?.uppercase()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = user.phoneNumber,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (user.userType) {
                        UserType.CUSTOMER -> MaterialTheme.colorScheme.primary
                        UserType.VENDOR -> MaterialTheme.colorScheme.secondary
                    }.copy(alpha = 0.1f)
                ),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = user.userType.name.lowercase().capitalize(Locale.ROOT),
                    color = when (user.userType) {
                        UserType.CUSTOMER -> MaterialTheme.colorScheme.primary
                        UserType.VENDOR -> MaterialTheme.colorScheme.secondary
                    }
                )
            }
        }
    }
}