var data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

var filtered = data.filter(function(i) {
  return i % 2 == 0;
});
print(filtered);

var sumOfFiltered = filtered.reduce(function(acc, next) {
  return acc + next;
}, 0);
print(sumOfFiltered); 