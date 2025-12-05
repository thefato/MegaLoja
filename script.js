document.addEventListener("DOMContentLoaded", () => {
    // --- Menu de Acessibilidade ---

    const accessibilityBtn = document.getElementById("btnAcessibilidade");
    const sidebar = document.getElementById("acessibilidade-menu");
    const closeBtn = document.getElementById("close-sidebar");

    // Abrir o menu
    if (accessibilityBtn) {
        accessibilityBtn.addEventListener("click", () => {
            sidebar.classList.add("active");
        });
    }

    // Fechar o menu
    if (closeBtn) {
        closeBtn.addEventListener("click", () => {
            sidebar.classList.remove("active");
        });
    }

    // Funções de Acessibilidade
    const increaseFontBtn = document.getElementById("increase-font");
    const decreaseFontBtn = document.getElementById("decrease-font");
    const contrastBtn = document.getElementById("contrast");

    let currentFontSize = 16; 

    if (increaseFontBtn) {
        increaseFontBtn.addEventListener("click", () => {
            currentFontSize += 1;
            document.body.style.fontSize = `${currentFontSize}px`;
        });
    }

    if (decreaseFontBtn) {
        decreaseFontBtn.addEventListener("click", () => {
            currentFontSize -= 1;
            document.body.style.fontSize = `${currentFontSize}px`;
        });
    }

    if (contrastBtn) {
        contrastBtn.addEventListener("click", () => {
            document.body.classList.toggle("high-contrast");
        });
    }
    
// --- Carrossel  ---

const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const slidesContainer = document.getElementById("slidesContainer");
const slides = document.querySelectorAll(".slide");

let currentSlideIndex = 0;
const totalSlides = slides.length;
const slideDuration = 6000;
let autoSlideTimer;

function updateCarousel() {
    // Calcula o percentual de movimento (100% / Número total de slides)
    // Se totalSlides = 3, move 33.3333% por vez
    const slideWidthPercentage = 100 / totalSlides; 
    
    // Calcula o deslocamento horizontal necessário
    const offset = -currentSlideIndex * slideWidthPercentage;
    
    slidesContainer.style.transform = `translateX(${offset}%)`;

    resetAutoSlideTimer();
}

// Avança para o próximo slide, voltando ao início se chegar ao fim.

function nextSlide() {
    currentSlideIndex = (currentSlideIndex + 1) % totalSlides;
    updateCarousel();
}

// Retrocede para o slide anterior, voltando ao fim se chegar ao início.
function prevSlide() {
    currentSlideIndex = (currentSlideIndex - 1 + totalSlides) % totalSlides;
    updateCarousel();
}


// Inicia ou reseta o timer de troca automática

function resetAutoSlideTimer() {
    clearInterval(autoSlideTimer);
    
    // Configura um novo timer para chamar nextSlide()
    autoSlideTimer = setInterval(nextSlide, slideDuration);
}

// Configurar Listeners de Botão
if (nextBtn) {
    nextBtn.addEventListener("click", nextSlide);
}
if (prevBtn) {
    prevBtn.addEventListener("click", prevSlide);
}

if (totalSlides > 0) {
    updateCarousel();
}

    // --- A seguir, feito totalmente por IA para vincular com o Back, verificar mais tarde!!!!!!!!

    // --- Lógica de Integração com a API (Produtos Recomendados) ---

    /**
     * Função que renderiza os produtos na tela a partir de um array de dados.
     * @param {Array} productsArray - Array de objetos de produto vindos da API.
     */
    function renderProducts(productsArray) {
        const container = document.getElementById('product-container');
        if (!container) return; 

        let productsHTML = '';

        productsArray.forEach(product => {
            // Formata o preço, garantindo que o campo 'price' exista
            const price = product.price || 0; // Evita erro se 'price' não vier
            const formattedPrice = price.toLocaleString('pt-BR', { 
                style: 'currency', 
                currency: 'BRL' 
            });

            // Template string para criar o HTML do card.
            // As chaves devem corresponder EXATAMENTE às chaves retornadas pela API (ex: product.name)
            productsHTML += `
                <a href="${product.link || '#'}" class="product-card">
                    <img src="${product.image || 'placeholder.png'}" alt="${product.name || 'Produto sem nome'}">
                    <p class="product-name">${product.name || 'Produto Indisponível'}</p>
                    <p class="product-price">${formattedPrice}</p>
                </a>
            `;
        });

        container.innerHTML = productsHTML;

        // Se a lista estiver vazia, pode ser útil mostrar uma mensagem:
        if (productsArray.length === 0) {
             container.innerHTML = "<p>Nenhum item recomendado encontrado no momento.</p>";
        }
    }



});


                                                                    /* --- Parte de Login --- */


document.addEventListener('DOMContentLoaded', () => {
    // Seleciona o toggle switch (checkbox)
    const toggle = document.getElementById('toggleSwitch');
    
    // Seleciona o container que contém o inner card (o que vai girar)
    const flipCard = document.querySelector('.flip-card');
    
    // Adiciona um listener para detectar mudanças no estado do checkbox
    if (toggle && flipCard) {
        toggle.addEventListener('change', function() {
            // Se o checkbox estiver marcado (checked), adiciona a classe 'flipped'
            if (this.checked) {
                flipCard.classList.add('flipped');
                
                // Atualiza o estilo dos labels para indicar o ativo
                document.querySelector('.label-login').style.color = '#888';
                document.querySelector('.label-register').style.color = '#000';
            } 
            // Caso contrário, remove a classe 'flipped'
            else {
                flipCard.classList.remove('flipped');
                
                document.querySelector('.label-login').style.color = '#000';
                document.querySelector('.label-register').style.color = '#888';
            }
        });
    }
});
