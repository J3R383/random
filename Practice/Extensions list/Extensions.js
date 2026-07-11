const toggles = document.querySelectorAll('.status-toggle');

toggles.forEach(toggle => {
    toggle.addEventListener('change', function () {
        // find the closest parent card
        const card = this.closest('.extensions-tab');
        if (!card) return;
        const label = card.querySelector('.status-label');

        if (this.checked) {
            card.classList.remove('inactive');
            label.textContent = 'Active';
        } else {
            card.classList.add('inactive');
            label.textContent = 'Inactive';
        }
    });
});

// function to allow the 'all', 'active only' and 'inactive only' buttons work
const filterButtons = document.querySelectorAll('.filter-button');
const allCards = document.querySelectorAll('.extensions-tab');

filterButtons.forEach(button => {
    button.addEventListener('click', () => {
        // update button styling on event
        filterButtons.forEach(b => b.classList.remove('active'));
        button.classList.add('active');

        const filterValue = button.getAttribute('data-filter');

        // filtering the grid
        allCards.forEach(card => {
            const isInactive = card.classList.contains('inactive');

            if (filterValue === 'all') {
                card.classList.remove('hidden-card');
            } else if (filterValue === 'active') {
                // show if it is active (not inactive)
                !isInactive ? card.classList.remove('hidden-card') : card.classList.add('hidden-card');
            } else if (filterValue === 'inactive') {
                // show if it is inactive
                isInactive ? card.classList.remove('hidden-card') : card.classList.add('hidden-card');
            }
        });
    });
});

// Select all remove buttons
const removeButtons = document.querySelectorAll('.remove-button');

removeButtons.forEach(button => {
    button.addEventListener('click', function() {
        const card = this.closest('.extensions-tab');
        if (!card) return;
        const cardName = card.querySelector('h2')?.textContent || 'this extension';

        const confirmDelete = confirm(`Are you sure you want to remove ${cardName}?`);
        if (confirmDelete) {
            card.remove();
            alert(`${cardName} has been removed.`);
        }
    });
});