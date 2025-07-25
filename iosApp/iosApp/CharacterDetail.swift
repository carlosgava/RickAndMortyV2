//
//  CharacterDetail.swift
//  iosApp
//
//  Created by Carlos Henrique Gava on 24/07/25.
//

import SwiftUI
import shared

struct CharacterDetail: View {
    @Environment(\.dismiss) private var dismiss
    let character: Characters
    
    var body: some View {
        VStack {
            ObjectDetails(character: character)
        }
        .navigationTitle("Detalhe")
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                Button(action: {
                    dismiss()
                }) {
                    Label("Voltar", systemImage: "arrow.left.circle")
                }
            }
        }
    }
}

struct ObjectDetails: View {
    var character: Characters
    @State private var isPortrait = false
    var body: some View {
        ScrollView {
            VStack {
                AsyncImage(url: URL(string: character.image)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                    case .success(let image):
                        image
                            .imageScale(.medium)
                            .scaledToFill()
                            .clipped()
                    default:
                        EmptyView()
                    }
                }
                Spacer()
                    .frame(height: 20)

                VStack(alignment: .leading, spacing: 6) {
                    Text(character.name)
                        .font(.title)

                    LabeledInfo(label: "Localização", data: character.location.name)
                    LabeledInfo(label: "Status", data: character.status)
                    LabeledInfo(label: "Especie", data: character.species)
                    LabeledInfo(label: "Genero", data: character.gender)
                    
                    List {
                        ForEach(character.episode, id: \.self) { exercise in
                            Text(exercise)
                        }
                    }
                    .listStyle(.inset)
                }
                .padding(16)
            }
        }
        .scrollDisabled(isPortrait)
        .onReceive(NotificationCenter.default.publisher(for: UIDevice.orientationDidChangeNotification)) { _ in
            guard let scene = UIApplication.shared.windows.first?.windowScene else { return }
            self.isPortrait = scene.interfaceOrientation.isPortrait
        }
    }
}


struct LabeledInfo: View {
    var label: String
    var data: String
    
    var body: some View {
        Spacer()
        Text("**\(label):** \(data)")
    }
}
