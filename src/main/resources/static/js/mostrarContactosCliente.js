$(document).ready(function($) {
  $('table').show();
  $('#dataTable').change(function() {
      $('table').show();
      var selection = $(this).val();
      if (selection === '-Todos-') {
          $('tr').show();
      }
      else {
          var dataset = $('#teq .contenidobusqueda').find('tr');
          // show all rows first
          dataset.show();
      }
      // filter the rows that should be hidden
      dataset.filter(function(index, item) {
          return $(item).find('#third-child').text().split(',').indexOf(selection) === -1;
      }).hide();
  });
});