package adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import helpers.ContextHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.AdapterRecyclerSupportedPlanBinding
import retrofit.models.SupportedPlan

class AdapterRecyclerSupportedPlanCompany(private val supportedPlans: List<SupportedPlan>, private val days: Int) : RecyclerView.Adapter<AdapterRecyclerSupportedPlanCompany.ViewHolder>() {
    private val colorBlack = ContextHelper.getColor(R.color.black)
    private val colorGray = ContextHelper.getColor(R.color.gray)
    private val colorGreen = ContextHelper.getColor(R.color.green)
    private val colorWhite = ContextHelper.getColor(R.color.white)
    private val marginPx = (4 * App.ACTIVITY.resources.displayMetrics.density).toInt() // 4dp in px

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = AdapterRecyclerSupportedPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = supportedPlans[position]
        val context = holder.itemView.context

        holder.b.tvCompany.text = item.company.name
        holder.b.tlPlans.removeAllViews()

        holder.b.btBuy.text = App.CONTENT.calculatorResultBuy
        holder.b.tvDetails.text = App.CONTENT.calculatorResultDetails

        // Header Row
        val headerRow = TableRow(context).apply { setBackgroundColor(colorWhite) }
        listOf(
            App.CONTENT.calculatorResultDeductible,
            App.CONTENT.calculatorResultUnit,
            App.CONTENT.calculatorResultTotal
        ).forEachIndexed { index, title ->
            headerRow.addView(TextView(context).apply {
                text = title
                setTextColor(colorGreen)
                setPadding(16)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f).apply {
                    if (index < 2) setMargins(0, 0, marginPx, 0)
                }
                gravity = Gravity.CENTER
            })
        }
        holder.b.tlPlans.addView(headerRow)

        // Data Rows
        item.deductibles.forEachIndexed { index, deductible ->
            val row = TableRow(context)

            // Column 1: Deductible
            row.addView(TextView(context).apply {
                text = buildString {
                    append("$")
                    append(deductible.amountValue)
                }
                setBackgroundColor(if (index % 2 == 0) colorWhite else colorGray)
                setTextColor(colorBlack)
                setPadding(16)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f).apply {
                    setMargins(0, 0, marginPx, 0)
                }
                gravity = Gravity.CENTER
            })

            // Column 2: Unit Price
            row.addView(TextView(context).apply {
                text = item.plans.joinToString("\n") { planItem ->
                    val cost = (planItem.prices.first().dailyCost ?: 0.0) * (1 - deductible.discountValue / 100.0)
                    buildString {
                        append("$")
                        append("%.2f".format(cost))
                    }
                }
                setBackgroundColor(if (index % 2 == 0) colorWhite else colorGray)
                setTextColor(colorBlack)
                setPadding(16)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f).apply {
                    setMargins(0, 0, marginPx, 0)
                }
                gravity = Gravity.CENTER
            })

            // Column 3: Total Price
            row.addView(TextView(context).apply {
                text = item.plans.joinToString("\n") { planItem ->
                    val cost = (planItem.prices.first().dailyCost ?: 0.0) * (1 - deductible.discountValue / 100.0)
                    buildString {
                        append("$")
                        append("%.2f".format(cost * days))
                    }
                }
                setBackgroundColor(if (index % 2 == 0) colorWhite else colorGray)
                setPadding(16)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f)
                gravity = Gravity.CENTER
            })

            holder.b.tlPlans.addView(row)
        }
    }

    override fun getItemCount(): Int = supportedPlans.size

    inner class ViewHolder(val b: AdapterRecyclerSupportedPlanBinding) : RecyclerView.ViewHolder(b.root)
}