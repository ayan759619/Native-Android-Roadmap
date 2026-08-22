@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composeprofile.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeprofile.Employee

private const val TAG = "EmployeeScreen"


// ---------------------------------------------------------
// Main Employee Screen
// ---------------------------------------------------------

@Composable
fun EmployeeScreen() {

    /*
     * Search text entered by the user.
     */
    var searchQuery by remember {
        mutableStateOf("")
    }

    /*
     * Currently selected department.
     *
     * "All" means show every employee.
     */
    var selectedDepartment by remember {
        mutableStateOf("All")
    }

    /*
     * Sample employee data.
     *
     * remember prevents creating a new list
     * on every recomposition.
     */
    val employees = remember {
        sampleEmployees()
    }


    // -----------------------------------------------------
    // Compose Lifecycle Logging
    // -----------------------------------------------------

    LaunchedEffect(Unit) {

        Log.d(
            TAG,
            "EmployeeScreen entered composition"
        )
    }

    DisposableEffect(Unit) {

        Log.d(
            TAG,
            "EmployeeScreen started"
        )

        onDispose {

            Log.d(
                TAG,
                "EmployeeScreen disposed"
            )
        }
    }


    // -----------------------------------------------------
    // Filter employees
    // -----------------------------------------------------

    val filteredEmployees = remember(
        employees,
        searchQuery,
        selectedDepartment
    ) {

        employees.filter { employee ->

            val matchesSearch =
                searchQuery.isBlank() ||
                        employee.name.contains(
                            searchQuery,
                            ignoreCase = true
                        ) ||
                        employee.role.contains(
                            searchQuery,
                            ignoreCase = true
                        ) ||
                        employee.department.contains(
                            searchQuery,
                            ignoreCase = true
                        ) ||
                        employee.email.contains(
                            searchQuery,
                            ignoreCase = true
                        )

            val matchesDepartment =
                selectedDepartment == "All" ||
                        employee.department == selectedDepartment

            matchesSearch && matchesDepartment
        }
    }


    // -----------------------------------------------------
    // Scaffold
    // -----------------------------------------------------

    Scaffold(

        topBar = {

            EmployeeTopBar()
        }

    ) { innerPadding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)

        ) {


            // -------------------------------------------------
            // Search Bar
            // -------------------------------------------------

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            EmployeeSearchBar(

                searchQuery = searchQuery,

                onSearchQueryChange = { newQuery ->

                    searchQuery = newQuery
                }
            )


            // -------------------------------------------------
            // Department Filter
            // -------------------------------------------------

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DepartmentFilterRow(

                employees = employees,

                selectedDepartment = selectedDepartment,

                onDepartmentSelected = { department ->

                    selectedDepartment = department
                }
            )


            // -------------------------------------------------
            // Employee Count
            // -------------------------------------------------

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "${filteredEmployees.size} employees found",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // -------------------------------------------------
            // Employee List / Empty State
            // -------------------------------------------------

            if (filteredEmployees.isEmpty()) {

                EmptyEmployeeState()

            } else {

                EmployeeList(
                    employees = filteredEmployees
                )
            }
        }
    }
}


// ---------------------------------------------------------
// Top App Bar
// ---------------------------------------------------------

@Composable
fun EmployeeTopBar() {

    TopAppBar(

        title = {

            Text(
                text = "Employees",
                fontWeight = FontWeight.Bold
            )
        }
    )
}


// ---------------------------------------------------------
// Search Bar
// ---------------------------------------------------------

@Composable
fun EmployeeSearchBar(

    searchQuery: String,

    onSearchQueryChange: (String) -> Unit

) {

    OutlinedTextField(

        value = searchQuery,

        onValueChange = { newValue ->

            onSearchQueryChange(newValue)
        },

        modifier = Modifier.fillMaxWidth(),

        placeholder = {

            Text(
                text = "Search employees..."
            )
        },

        leadingIcon = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },

        trailingIcon = {

            if (searchQuery.isNotEmpty()) {

                IconButton(

                    onClick = {

                        onSearchQueryChange("")
                    }

                ) {

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },

        singleLine = true
    )
}


// ---------------------------------------------------------
// Department LazyRow
// ---------------------------------------------------------

@Composable
fun DepartmentFilterRow(

    employees: List<Employee>,

    selectedDepartment: String,

    onDepartmentSelected: (String) -> Unit

) {

    /*
     * Create unique departments.
     *
     * Example:
     *
     * All
     * Mobile
     * Backend
     * QA
     * Design
     * HR
     */

    val departments = remember(employees) {

        listOf("All") +
                employees
                    .map { employee ->
                        employee.department
                    }
                    .distinct()
    }


    LazyRow(

        horizontalArrangement = Arrangement.spacedBy(8.dp),

        contentPadding = PaddingValues(
            horizontal = 4.dp
        )

    ) {

        items(

            items = departments,

            /*
             * Department name is unique,
             * therefore it can be used as the key.
             */

            key = { department ->

                department
            }

        ) { department ->


            AssistChip(

                onClick = {

                    onDepartmentSelected(
                        department
                    )
                },

                label = {

                    Text(
                        text = department
                    )
                }
            )
        }
    }
}


// ---------------------------------------------------------
// Employee LazyColumn
// ---------------------------------------------------------

@Composable
fun EmployeeList(

    employees: List<Employee>

) {

    LazyColumn(

        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        verticalArrangement = Arrangement.spacedBy(
            12.dp
        ),

        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 24.dp
        )

    ) {

        items(

            items = employees,

            /*
             * IMPORTANT:
             *
             * Use a stable unique ID.
             *
             * This helps Compose identify
             * each item when the list changes.
             */

            key = { employee ->

                employee.id
            }

        ) { employee ->


            EmployeeCard(
                employee = employee
            )
        }
    }
}


// ---------------------------------------------------------
// Employee Card
// ---------------------------------------------------------

@Composable
fun EmployeeCard(

    employee: Employee

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {


            // ---------------------------------------------
            // Avatar
            // ---------------------------------------------

            EmployeeAvatar(
                name = employee.name
            )


            Spacer(
                modifier = Modifier.width(16.dp)
            )


            // ---------------------------------------------
            // Employee Information
            // ---------------------------------------------

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(

                    text = employee.name,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold
                )


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Text(

                    text = employee.role,

                    fontSize = 14.sp
                )


                Spacer(
                    modifier = Modifier.height(2.dp)
                )


                Text(

                    text = employee.department,

                    fontSize = 13.sp,

                    color = MaterialTheme
                        .colorScheme
                        .primary,

                    fontWeight = FontWeight.Medium
                )


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Text(

                    text = employee.email,

                    fontSize = 13.sp
                )
            }
        }
    }
}


// ---------------------------------------------------------
// Employee Avatar
// ---------------------------------------------------------

@Composable
fun EmployeeAvatar(

    name: String

) {

    Card(

        modifier = Modifier.size(56.dp),

        shape = MaterialTheme.shapes.medium,

        colors = CardDefaults.cardColors(

            containerColor =
                MaterialTheme.colorScheme
                    .primaryContainer
        )

    ) {

        Box(

            modifier = Modifier.fillMaxSize(),

            contentAlignment = Alignment.Center

        ) {

            Text(

                text = name
                    .firstOrNull()
                    ?.uppercase()
                    ?: "?",

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold,

                color = MaterialTheme
                    .colorScheme
                    .onPrimaryContainer
            )
        }
    }
}


// ---------------------------------------------------------
// Empty State
// ---------------------------------------------------------

@Composable
fun EmptyEmployeeState() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),

        contentAlignment = Alignment.Center

    ) {

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Text(

                text = "No employees found",

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(

                text =
                    "Try another search or department."
            )
        }
    }
}


// ---------------------------------------------------------
// Sample Employee Data
// ---------------------------------------------------------

fun sampleEmployees(): List<Employee> {

    return listOf(

        Employee(

            id = 1,

            name = "Ayan Karmakar",

            role = "Android Developer",

            department = "Mobile",

            email = "ayan@example.com"
        ),


        Employee(

            id = 2,

            name = "Rahul Sharma",

            role = "Backend Developer",

            department = "Backend",

            email = "rahul@example.com"
        ),


        Employee(

            id = 3,

            name = "Priya Singh",

            role = "UI/UX Designer",

            department = "Design",

            email = "priya@example.com"
        ),


        Employee(

            id = 4,

            name = "Arjun Mehta",

            role = "QA Engineer",

            department = "QA",

            email = "arjun@example.com"
        ),


        Employee(

            id = 5,

            name = "Sneha Das",

            role = "HR Manager",

            department = "HR",

            email = "sneha@example.com"
        ),


        Employee(

            id = 6,

            name = "Rohit Kumar",

            role = "Android Developer",

            department = "Mobile",

            email = "rohit@example.com"
        ),


        Employee(

            id = 7,

            name = "Neha Gupta",

            role = "Backend Developer",

            department = "Backend",

            email = "neha@example.com"
        ),


        Employee(

            id = 8,

            name = "Vikram Singh",

            role = "Senior Android Developer",

            department = "Mobile",

            email = "vikram@example.com"
        ),


        Employee(

            id = 9,

            name = "Kavya Rao",

            role = "QA Engineer",

            department = "QA",

            email = "kavya@example.com"
        ),


        Employee(

            id = 10,

            name = "Ravi Patel",

            role = "Product Manager",

            department = "Product",

            email = "ravi@example.com"
        )
    )
}