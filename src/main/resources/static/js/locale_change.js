document.addEventListener('DOMContentLoaded', () => {
    console.log('Loaded');
    const select = document.getElementById('locales');
    select.onchange =
        function my(e) {
            const option = select.value;
            if (option != '') {
                window.location.replace('/home?lang=' + option);
            }
        }
})
