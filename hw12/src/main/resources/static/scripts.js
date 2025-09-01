function fetchAuthors(displayAuthors) {
    fetch('/api/authors')
        .then(response => response.json())
        .then(json => displayAuthors(json));
}

function fetchGenres(displayGenres) {
    fetch('/api/genres')
        .then(response => response.json())
        .then(json => displayGenres(json));
}

function fetchBooks(displayBooks) {
    fetch('/api/books')
        .then(response => response.json())
        .then(json => displayBooks(json));
}

function fetchGenresForBook(bookId, displayGenres) {
    fetch(`/api/books/${bookId}/genres`)
            .then(rawResponse => rawResponse.json())
            .then(json => displayGenres(json));
}

function fetchCommentsForBook(bookId, displayComments) {
    fetch(`/api/books/${bookId}/comments`)
            .then(rawResponse => rawResponse.json())
            .then(json => displayComments(json));
}

function fetchBookById(bookId, displayBook, displayAuthors, displayGenres) {
    Promise.all([fetch(`/api/books/${bookId}`), fetch('/api/authors'), fetch('/api/genres')])
        .then(responses => Promise.all([responses[0].json(), responses[1].json(), responses[2].json()]))
        .then(data => {
            const book = data[0];
            const authors = data[1];
            const genres = data[2];

            displayBook(book);
            displayAuthors(authors, book.authorId);
            displayGenres(genres, book.genreIds);
        });
}

function postBook(book, onResponseOk) {
    const csrfToken = getCsrfToken();
    const csrfHeader = getCsrfHeaderName();

    fetch('/api/books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: book,
    }).then(response => {
        if (response.ok) {
            onResponseOk();
        }
    });
}

function putBook(book, onResponseOk) {
    const csrfToken = getCsrfToken();
    const csrfHeader = getCsrfHeaderName();

    fetch('/api/books', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: book,
    }).then(response => {
        if (response.ok) {
            onResponseOk();
        }
    });
}

function deleteBook(bookId) {
    const csrfToken = getCsrfToken();
    const csrfHeader = getCsrfHeaderName();

    fetch(`/api/books/${bookId}`, {
        method: 'DELETE',
        headers: {
            [csrfHeader]: csrfToken,
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (response.ok) {
            goHome();
        }
    })
}

function goHome() {
    window.location.href = '/';
}

function getCsrfToken() {
    const name = 'XSRF-TOKEN';
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}

function getCsrfHeaderName() {
    const metaTag = document.querySelector('meta[name="_csrf_header"]');
    return metaTag ? metaTag.getAttribute('content') : 'X-XSRF-TOKEN';
}