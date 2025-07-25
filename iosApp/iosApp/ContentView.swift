import SwiftUI
import shared


extension Characters: @retroactive Identifiable {}

struct ContentView: View {
    @StateObject var viewModel = SharedViewModel<CharactersViewModel>()
    @State var characters: [Characters] = []
    @State var isLoading: Bool = false
    
    let columns = [
        GridItem(.adaptive(minimum: 120), alignment: .top)
    ]
    
    var body: some View {
        NavigationStack {
            ScrollView {
                LazyVGrid(columns: columns, alignment: .leading, spacing: 20) {
                    ForEach($characters.wrappedValue, id: \.self) { item in
                        NavigationLink(destination: CharacterDetail(character: item)) {
                            ObjectFrame(char: item)
                        }
                        .buttonStyle(PlainButtonStyle())
                    }
                }
            }
        }.task {
            viewModel.instance.loadCharacters()
        }
        .collect(flow: viewModel.instance.uiState) { flow in
            $characters.wrappedValue.append(contentsOf: flow.characters)
        }
        .padding(.all, 5)
    }
}

struct ObjectFrame: View {
    let char: Characters

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            GeometryReader { geometry in
                AsyncImage(url: URL(string: char.image)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                            .clipped()
                            .aspectRatio(1, contentMode: .fill)
                    default:
                        EmptyView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    }
                }
            }
            .aspectRatio(1, contentMode: .fit)

            Text(char.name)
                .font(.headline)

            Text(char.location.name)
                .font(.subheadline)

            Text(char.status)
                .font(.caption)
        }
    }
}
