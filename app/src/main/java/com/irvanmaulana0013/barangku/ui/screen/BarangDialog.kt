package com.irvanmaulana0013.barangku.ui.screen

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.irvanmaulana0013.barangku.R
import com.irvanmaulana0013.barangku.ui.theme.BarangkuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarangDialog(
    bitmap: Bitmap?,
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String, String) -> Unit
) {
    var namaBarang by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val categories = listOf(
        stringResource(R.string.peralatan),
        stringResource(R.string.perlengkapan),
        stringResource(R.string.kendaraan),
        stringResource(R.string.pakaian),
        stringResource(R.string.elektronik),
        stringResource(R.string.lainnya)
    )


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                )
                OutlinedTextField(
                    value = namaBarang,
                    onValueChange = { namaBarang = it },
                    label = { Text(text = stringResource(R.string.nama_barang)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    OutlinedTextField(
                        value = kategori,
                        onValueChange = {},
                        label = { Text(text = stringResource(R.string.kategori)) },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        categories.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    kategori = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = jumlah,
                    onValueChange = { jumlah = it },
                    label = { Text(text = stringResource(R.string.jumlah)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.batal))
                    }
                    OutlinedButton(
                        onClick = { onConfirmation(namaBarang, kategori, jumlah) },
                        enabled = namaBarang.isNotEmpty() &&
                                kategori.isNotEmpty() && jumlah.isNotEmpty(),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.simpan))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddDialogPreview() {
    BarangkuTheme {
        BarangDialog(
            bitmap = null,
            onDismissRequest = {},
            onConfirmation = {_, _, _ ->}
        )
    }
}