# Simulations

Aby rozpocząc symulację, uruchom klasę World. Przycisk "Start with old configuration" odpala symulację z poprzednią konfiguracją (lub domyślnie załączoną, jeśli jest uruchamiana pierwszy raz). 

UWAGA: może się wydarzyć wyjątek NoSuchFileException - z jakiegoś powodu czasami niepoprawnie znajdowana jest lokalizacja pliku config.JSON. Aby to naprawić, trzeba w klasie Project/Model/Core/SimulationConfig.java w metodzie get() zmienić lokalizację (zazwyczaj działa dodanie "/Simulations/" na początku) i taką samą zmianę wprowadzić w Project/GUI/SimulationMenu.java w metodzie changeJSON().
