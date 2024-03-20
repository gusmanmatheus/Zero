package com.example.zero.list

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.zero.Client
import com.example.zero.R
import com.example.zero.databinding.ClientItemBinding

class ClientsAdapter : RecyclerView.Adapter<ClientsAdapter.ViewHolder>() {
    private var client: List<Client> = listOf()

    fun setData(data: List<Client>) {
        client = data
        this.notifyItemRangeChanged(0, data.lastIndex)
    }

    inner class ViewHolder(private val binding: ClientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun handler(client: Client) {
            binding.nameCi.text = client.name
            binding.genderCi.text = client.gender.name
            val color: Int = when (client.gender) {
                Gender.MALE -> ContextCompat.getColor(binding.root.context, R.color.blue)
                Gender.FEMALE -> ContextCompat.getColor(binding.root.context, R.color.pink)
                else -> ContextCompat.getColor(binding.root.context, R.color.green)
            }
            binding.genderImageCi.setImageDrawable(getCircleImage(color))
            binding.counterCi.text = client.counter.toString()
        }
    }


    fun getCircleImage(@ColorInt colorGender: Int): Drawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(colorGender)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ClientItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = client.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.handler(client[position])
    }
}