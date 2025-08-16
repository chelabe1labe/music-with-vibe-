const searchBtn = document.getElementById('search-btn');
const searchInput = document.getElementById('search-input');
const musicList = document.getElementById('music-list');

searchBtn.addEventListener('click', async () => {
    const query = searchInput.value.trim();
    if (!query) return alert("Enter a search term");

    musicList.innerHTML = "Loading...";
    try {
        const response = await fetch(`http://localhost:8080/api/music/search?query=${encodeURIComponent(query)}`);
        const songs = await response.json();
        musicList.innerHTML = '';

        songs.forEach(song => {
            const div = document.createElement('div');
            div.classList.add('music-item');
            div.innerHTML = `
                <img src="${song.cover_art}" class="cover-art">
                <h3>${song.title}</h3>
                <p>${song.artist}</p>
                <iframe width="250" height="140" src="${song.youtube_preview}" frameborder="0" allowfullscreen></iframe>
                <a href="${song.url}" target="_blank" class="download-btn">Download / Listen</a>
            `;
            musicList.appendChild(div);
        });
    } catch(e) {
        musicList.innerHTML = "Error fetching songs";
    }
});
