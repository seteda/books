data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

filtered = data.findAll {!(it % 2)}
println filtered

sumOfFiltered = filtered.inject(0) { acc, val -> acc + val }
println sumOfFiltered