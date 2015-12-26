<#
for(var i=0; i < itemArrayData.length; i++)
{
   var item = itemArrayData[i];
#>
   <p><#=item.name #>:<#=item.value #></p>
<# } #>
